package com.dellepiane.ceibatest.domain.usecases

import app.cash.turbine.test
import com.dellepiane.ceibatest.domain.model.UserPost
import com.dellepiane.ceibatest.domain.repository.UserPostsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.test.assertEquals

class GetUserPostsUseCaseTest {

    @RelaxedMockK
    private lateinit var userPostRepository: UserPostsRepository

    private lateinit var objectUnderTest: GetUserPostsUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        setUpGetUserPostsUseCase()
    }

    @Test
    fun `Getting user posts should emit Success when service finishes with success`() = runTest {
        val userId = 1
        val userPost = UserPost(1, "title", "body")
        coEvery { userPostRepository.getUserPosts(userId) } returns flowOf(
            Result.success(
                listOf(
                    userPost
                )
            )
        )

        objectUnderTest.execute(userId).test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(listOf(userPost)),
                actual = result
            )
            awaitComplete()
        }
    }

    @Test
    fun `Getting user posts should emit Internet connection error when service finishes with internet connection error`() =
        runTest {
            val testException = IOException("Internet error message")
            val userId = 1
            val userPost = UserPost(1, "title", "body")
            coEvery { userPostRepository.getUserPosts(userId) } throws testException andThen flowOf(
                Result.success(
                    listOf(userPost)
                )
            )

            assertThrows<IOException> {
                objectUnderTest.execute(userId).test {
                    val errorResult = awaitItem()

                    assertEquals(
                        expected = Result.failure(testException),
                        actual = errorResult
                    )

                    val itemsResult = awaitItem()

                    assertEquals(
                        expected = Result.success(listOf(userPost)),
                        actual = itemsResult
                    )
                }
            }
        }

    @Test
    fun `Getting user posts should emit error when service finishes with error`() = runTest {
        val testException = Exception("Error message")
        val userId = 1
        coEvery { userPostRepository.getUserPosts(userId) } throws testException

        assertThrows<Exception> {
            objectUnderTest.execute(userId).test {
                val result = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = result
                )
            }
        }
    }

    private fun setUpGetUserPostsUseCase() {
        objectUnderTest = GetUserPostsUseCase(userPostRepository)
    }

}