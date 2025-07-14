const btnMenu = document.querySelector("#button-toggle");
const navBar = document.querySelector("#nav");
const menuHam = document.querySelector(".box-menu");

btnMenu.addEventListener('click', () => {
	navBar.classList.toggle("active-navbar");
	menuHam.classList.toggle("show");
});