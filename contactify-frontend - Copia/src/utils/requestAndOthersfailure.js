const messageFailureRequest = document.querySelector(".message-failure-request");
const buttonClose = document.querySelector("#button-close-message-failure-request");

function openMessageFailureRequest(dataError) {
	messageFailureRequest.querySelector(".message-failure-request-text-title").innerHTML = dataError.title;
	messageFailureRequest.querySelector(".message-failure-request-text-body").innerHTML = dataError.body;

	setTimeout(() => {
		messageFailureRequest.classList.add("show");

		setInterval(() => {
			messageFailureRequest.classList.remove("show");
		}, 6000);
	}, 500);
	
}

function closeMessageFailureRequest() {	
	messageFailureRequest.classList.remove("show");
}

if (buttonClose) {
	buttonClose.addEventListener('click', closeMessageFailureRequest);	
}

export { openMessageFailureRequest, closeMessageFailureRequest};