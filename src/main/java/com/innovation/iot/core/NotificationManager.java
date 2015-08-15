package com.innovation.iot.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.iot.domain.Device;
import com.innovation.iot.domain.Message;
import com.innovation.iot.domain.User;
import com.innovation.iot.persistence.MessageDao;
import com.innovation.iot.representation.Notification;

public class NotificationManager {

	private static final NotificationManager instance = new NotificationManager();

	private NotificationManager() {

	}

	public static NotificationManager getInstance() {
		return instance;
	}

	public String getNotifications(String deviceId, String userCode) {
		MessageDao dao = new MessageDao();
		ObjectMapper mapper = new ObjectMapper();
		String notifications = "";
		Notification notification = new Notification();
		try {
			List<Message> messages = dao.getMessages(deviceId, userCode);
			User user = dao.getUser(userCode);
			Device device = dao.getDevice(deviceId);
			notification.setAnnouncements(messages);
			notification.setUser(user);
			notification.setDevice(device);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			notifications = mapper.writeValueAsString(notification);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notifications;
	}

}
