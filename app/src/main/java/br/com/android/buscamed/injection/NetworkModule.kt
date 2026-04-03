package br.com.android.buscamed.injection

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import br.com.android.buscamed.BuildConfig
import io.ktor.client.plugins.HttpTimeout

/**
 * Módulo de injeção de dependências responsável por prover as instâncias relacionadas à comunicação de rede.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provê uma instância singleton do [HttpClient] configurado com motor OkHttp,
     * negociação de conteúdo JSON, logs detalhados e interceptação de autenticação via Firebase.
     *
     * @param firebaseAuth Instância do FirebaseAuth utilizada para recuperar e atualizar os tokens de sessão.
     * @return Cliente HTTP Ktor configurado.
     */
    @Provides
    @Singleton
    fun provideHttpClient(firebaseAuth: FirebaseAuth): HttpClient {
        return HttpClient(OkHttp) {
            install(HttpTimeout) {
                this.requestTimeoutMillis = 10_000
                this.connectTimeoutMillis = 5_000
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorClient", message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                url(BuildConfig.BASE_URL)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val user = firebaseAuth.currentUser
                        val token = user?.getIdToken(false)?.await()?.token
                        
                        if (token != null) {
                            BearerTokens(accessToken = token, refreshToken = "")
                        } else {
                            null
                        }
                    }
                    
                    refreshTokens {
                        val user = firebaseAuth.currentUser
                        val token = user?.getIdToken(true)?.await()?.token
                        
                        if (token != null) {
                            BearerTokens(accessToken = token, refreshToken = "")
                        } else {
                            null
                        }
                    }
                }
            }
        }
    }
}