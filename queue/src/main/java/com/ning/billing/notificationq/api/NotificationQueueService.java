/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.notificationq.api;

import java.util.UUID;

import org.joda.time.DateTime;

import com.ning.billing.queue.api.QueueLifecycle;

/**
 * A service to create and delete NotificationQueue
 */
public interface NotificationQueueService extends QueueLifecycle {

    public interface NotificationQueueHandler {

        /**
         * Called for each notification ready
         *
         * @param eventJson  the notification key associated to that notification entry
         * @param userToken  user token associated with that notification entry
         * @param searchKey1 the searchKey1 associated with that notification entry
         * @param searchKey2 the searchKey2 associated with that notification entry
         */
        public void handleReadyNotification(NotificationEventBase eventJson, DateTime eventDateTime, UUID userToken, Long searchKey1, Long searchKey2);
    }

    public static final class NotificationQueueAlreadyExists extends Exception {

        private static final long serialVersionUID = 1541281L;

        public NotificationQueueAlreadyExists(final String msg) {
            super(msg);
        }
    }

    public static final class NoSuchNotificationQueue extends Exception {

        private static final long serialVersionUID = 1541281L;

        public NoSuchNotificationQueue(final String msg) {
            super(msg);
        }
    }

    /**
     * Creates a new NotificationQueue for a given associated with the given service and queueName
     *
     * @param svcName   the name of the service using that queue
     * @param queueName a name for that queue (unique per service)
     * @param handler   the handler required for notifying the caller of state change
     * @return          a new NotificationQueue
     * @throws NotificationQueueAlreadyExists is the queue associated with that service and name already exits
     */
    public NotificationQueue createNotificationQueue(final String svcName, final String queueName, final NotificationQueueHandler handler)
            throws NotificationQueueAlreadyExists;

    /**
     * Retrieves an already created NotificationQueue by service and name if it exists
     *
     * @param svcName   the name of the service using that queue
     * @param queueName a name for that queue (unique per service)
     * @return          a new NotificationQueue
     * @throws NoSuchNotificationQueue if queue does not exist
     */
    public NotificationQueue getNotificationQueue(final String svcName, final String queueName)
            throws NoSuchNotificationQueue;

    /**
     * Delete notificationQueue
     *
     * @param svcName   the name of the service using that queue
     * @param queueName a name for that queue (unique per service)
     * @return          a new NotificationQueue
     * @throws NoSuchNotificationQueue if queue does not exist
     */
    public void deleteNotificationQueue(final String svcName, final String queueName)
            throws NoSuchNotificationQueue;

}
