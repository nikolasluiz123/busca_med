package br.com.android.buscamed.data.datasource.remote

import br.com.android.buscamed.data.core.network.safeApiCall
import br.com.android.buscamed.data.datasource.remote.dto.PrescriptionResponseDTO
import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.File
import javax.inject.Inject

/**
 * Implementação da fonte de dados remota utilizando o Ktor Client.
 *
 * @property httpClient Cliente HTTP configurado para as requisições.
 */
class PrescriptionRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : PrescriptionRemoteDataSource {

    override suspend fun processPrescriptionText(text: String, file: File, metadata: ExecutionMetadata): PrescriptionResponseDTO {
        return safeApiCall {
            httpClient.post("/v1/prescription/process/text") {
                contentType(ContentType.Application.Json)
                setBody(buildMultiPartFrom(file, text, metadata.pipelineVersion))
            }
        }
    }

    override suspend fun processPrescriptionImage(text: String, file: File, metadata: ExecutionMetadata): PrescriptionResponseDTO {
        return safeApiCall {
            httpClient.post("/v1/prescription/process/image") {
                setBody(buildMultiPartFrom(file, text, metadata.pipelineVersion))
            }
        }
    }

    private fun buildMultiPartFrom(file: File, text: String, pipelineVersion: String): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append(
                    "image",
                    file.readBytes(),
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                    }
                )
                append("text", text)
                append("pipelineVersion", pipelineVersion)
            }
        )
    }
}