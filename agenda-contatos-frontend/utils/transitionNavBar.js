const nav = document.querySelector("#nav");
const buttonIconMenu = document.querySelector("#icon-menu");

buttonIconMenu.addEventListener("click", function() {
	nav.classList.toggle("transition-nav");
});