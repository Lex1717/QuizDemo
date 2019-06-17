package com.alexeeff.golangpuzzler.puzzler.data.storage

import com.alexeeff.golangpuzzler.core.interfaces.RealmWrapper
import com.alexeeff.golangpuzzler.core.utils.applyLimitOffset
import com.alexeeff.golangpuzzler.core.utils.cascadeDeleteAllFromRealm
import com.alexeeff.golangpuzzler.puzzler.data.storage.entity.*
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.CourseListContainer
import com.alexeeff.golangpuzzler.puzzler.domain.entity.exception.GettingFromDatabaseException
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

/**
 * @author Yaroslav Alexeev
 */
class CourseStorageImpl(private val realmWrapper: RealmWrapper) : CourseStorage {

    override fun storeCourse(
            courseContainer: CourseContainer
    ): Boolean {
        val realm = realmWrapper.getRealmInstance()
        return try {
            realm.beginTransaction()
            storeCourseContainerData(realm, courseContainer)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
            false
        } finally {
            realm.close()
        }
    }

    private fun storeCourseContainerData(
            realm: Realm,
            container: CourseContainer
    ) {
        val cardsList =
                realm.where<CardStoredObject>()
                        .equalTo(COURSE_ID, container.courseId)
                        .findAll()
        cardsList.cascadeDeleteAllFromRealm()

        for (card in container.cardsList) {
            val createCardStoredObject = createCardStoredObject(card, container.courseId)
            realm.copyToRealm(createCardStoredObject)
        }
    }

    override fun getCourse(courseId: Int): CourseContainer {
        val realm = realmWrapper.getRealmInstance()
        return try {
            val cardsList =
                    realm.where<CardStoredObject>()
                            .equalTo(COURSE_ID, courseId)
                            .findAll()
                            .sort(ID, Sort.ASCENDING)

            val courseContainer = CourseContainer(courseId, createCardsList(cardsList))
            courseContainer
        } catch (e: Exception) {
            throw GettingFromDatabaseException("Error at getting course container")
        } finally {
            realm.close()
        }
    }

    override fun storeCourseList(courseContainer: CourseListContainer): Boolean {
        val realm = realmWrapper.getRealmInstance()
        return try {
            realm.beginTransaction()
            storeCourseListData(realm, courseContainer)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
            false
        } finally {
            realm.close()
        }
    }

    private fun storeCourseListData(
            realm: Realm,
            courseContainer: CourseListContainer
    ) {
        storeCourseTimeStamp(realm, courseContainer.timestamp)
        for (course in courseContainer.courseList) {
            realm.copyToRealmOrUpdate(createCourseStoredObject(course))
        }
    }

    private fun storeCourseTimeStamp(realm: Realm, timestamp: Long) {
        realm.where<TimestampStoredObject>()
                .findFirst()
                ?.deleteFromRealm()

        realm.copyToRealm(TimestampStoredObject(timestamp))
    }

    override fun clearCourseList() {
        val realm = realmWrapper.getRealmInstance()
        try {
            realm.beginTransaction()

            val courseList =
                    realm.where<CourseStoredObject>()
                            .findAll()
            courseList.deleteAllFromRealm()

            realm.commitTransaction()
        } catch (e: Exception) {
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            realm.close()
        }
    }

    override fun getCourseList(
            page: Int,
            size: Int
    ): CourseListContainer {
        val realm = realmWrapper.getRealmInstance()
        return try {
            val courseRealmResult =
                    realm.where<CourseStoredObject>()
                            .findAll()
                            .applyLimitOffset(size, (page - 1) * size)

            val timestamp = getCourseTimestamp(realm)

            CourseListContainer(createCourseList(courseRealmResult), timestamp)
        } catch (e: Exception) {
            throw GettingFromDatabaseException("Error at getting course list container")
        } finally {
            realm.close()
        }
    }

    private fun getCourseTimestamp(realm: Realm): Long {
        return realm.where<TimestampStoredObject>()
                .findFirst()
                ?.timestamp ?: 0
    }
}