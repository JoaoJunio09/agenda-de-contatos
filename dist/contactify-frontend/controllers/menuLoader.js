"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const navBarController_js_1 = require("./navBarController.js");
document.addEventListener('DOMContentLoaded', () => {
    fetch("../components/menu.html")
        .then(response => response.text())
        .then(data => {
        const header = document.getElementById("header");
        if (header) {
            header.innerHTML = data;
            (0, navBarController_js_1.initNavBar)();
        }
    })
        .catch(error => {
        console.log("Erro ao incorporar o menu", error);
    });
});
