document.addEventListener('DOMContentLoaded', () => {
	document.documentElement.classList.add("theme-light");

	const html = document.documentElement;
	const width = window.innerWidth;

	if (width < 1000) {
		html.classList.remove("theme-light");
		html.classList.add("theme-dark");
	}
	else {
		html.classList.add("theme-light");
		html.classList.remove("theme-dark");
	}

	document.querySelector("#button-alterar-tema").addEventListener('click', () => {
		const selectTheme = document.querySelector("#theme").value;
		setTheme(selectTheme);
	});
});

function setTheme(theme) {
	document.documentElement.className = theme;
	localStorage.setItem("theme", theme);
}