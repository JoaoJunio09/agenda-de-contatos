const messageFailure = document.querySelector(".message-failure");
const detailsSpan = document.querySelector("#span-details");
const buttonClose = document.querySelector("#button-close-message-failure");

function openMessageFailure(message) {
	messageFailure.querySelector(".message-failure p").innerHTML = message;
	messageFailure.classList.add("show");

	setInterval(() => {
		messageFailure.classList.remove("show");
	}, 4000);
}

function closeMessageFailure() {
	messageFailure.classList.remove("show");
}

function openSpanDetails(details) {
	detailsSpan.innerHTML = details;
}

function closeSpanDetails() {
	detailsSpan.innerHTML = "";
}

buttonClose.addEventListener('click', closeMessageFailure);

export { openMessageFailure, openSpanDetails, closeMessageFailure, closeSpanDetails };