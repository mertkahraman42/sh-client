package com.dipumba.ytsocialapp.post.data.model

import com.dipumba.ytsocialapp.common.util.DateFormatter
import com.dipumba.ytsocialapp.post.domain.model.PostComment
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

// Main response data - api model
@Serializable
internal data class RemotePostComment(
    val commentId: Long,
    val content: String,
    val postId: Long,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val createdAt: String
) {
    fun toDomainPostComment(): PostComment {
        return PostComment(
            commentId = commentId,
            content = content,
            postId = postId,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            createdAt = DateFormatter.parseDate(createdAt)
        )
    }
}

// HTTP response
@Serializable
internal data class GetPostCommentsResponseData(
    val success: Boolean,
    val comments: List<RemotePostComment> = listOf(),
    val message: String? = null
)

// Request params
internal data class GetPostCommentsApiResponse(
    val code: HttpStatusCode,
    val data: GetPostCommentsResponseData
)

// Making a comment

// Main response data - api model
@Serializable
internal data class CommentResponseData(
    val success: Boolean,
    val comment: RemotePostComment? = null,
    val message: String? = null
)

// HTTP response
internal data class CommentApiResponse(
    val code: HttpStatusCode,
    val data: CommentResponseData
)

// Request params (add comment)
@Serializable
internal data class NewCommentParams(
    val content: String,
    val postId: Long,
    val userId: Long
)

// Request params (remove comment)
@Serializable
internal data class RemoveCommentParams(
    val postId: Long,
    val commentId: Long,
    val userId: Long
)
