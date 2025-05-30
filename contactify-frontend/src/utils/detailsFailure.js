const failure = document.querySelector(".container-details-failure");
const btnClose = document.querySelector("#btnCloseFailure");

export function showDetailsFailure() {
	failure.classList.add("show-details-failure");

	btnClose.addEventListener('click', () => {
		failure.classList.remove("show-details-failure");
	});
	
	setTimeout(() => {
		failure.classList.remove("show-details-failure");
	}, 5500);
}