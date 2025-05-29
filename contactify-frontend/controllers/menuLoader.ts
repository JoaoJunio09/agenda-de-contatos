import { initNavBar } from "./navBarController.js";

document.addEventListener('DOMContentLoaded', () => {
  fetch("../components/menu.html")
    .then(response => response.text())
    .then(data => {
      const header = document.getElementById("header");

      if (header) {
        header.innerHTML = data;
        initNavBar();
      }
    })
    .catch(error => {
      console.log("Erro ao incorporar o menu", error);
    });
});
