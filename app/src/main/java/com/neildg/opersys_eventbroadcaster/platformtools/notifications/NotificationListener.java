
package com.neildg.opersys_eventbroadcaster.platformtools.notifications;

/**
 * Add this to the one who will listen to a certain notification
 * @author user
 *
 */
public interface NotificationListener {

	void onNotify(String notificationString, Parameters params);
}
