"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.initNavBar = initNavBar;
function initNavBar() {
    const button = document.querySelector("#button-toggle");
    const navBar = document.querySelector("#nav");
    if (button && navBar) {
        button.addEventListener('click', () => {
            button.classList.toggle("show");
            navBar.classList.toggle("active-navbar");
        });
    }
}
