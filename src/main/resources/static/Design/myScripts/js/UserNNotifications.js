function notificationsOpenedPanel(notificationID) {
	$.ajax({

		url : "Panel_NotificationUpdate",
		data : {
			'notificationID' : notificationID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true") {

				$("#body").load(location.href + "#body");

			}

		}

	})

};

function notificationDeletePanel(notificationID) {
	$.ajax({

		url : "Panel_NotificationDelete",
		data : {
			'notificationID' : notificationID
		},
		success : function(data, textStatus, jqXHR) {
			if (data === "true") {

				$("#body").load(location.href + "#body");

			}
		}

	})

};

function notificationListPanel() {
	$.ajax({

		url : "NotificationsPanel",
		success : function(data, textStatus, jqXHR) {
			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "NotificationsPanel";
			history.pushState({}, "", url);
		}

	})
	
};

unreadNotifications();
function unreadNotifications() {
	console.log("loaded");

	$.ajax({

		url : "NotificationsUnread",
		success : function(data, textStatus, jqXHR) {
			$('#unreadNotifications').text(data);
		}

	});
	
	setTimeout(unreadNotifications, 10000);
};

