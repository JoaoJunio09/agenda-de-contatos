const modal = document.querySelector(".modal-nova-conta");
const fade = document.querySelector(".fade");
const buttonOpenModal = document.querySelector("#button-open-modal");
const buttonExitModal = document.querySelector("#button-exit-modal");

buttonOpenModal.addEventListener('click', () => {
  modal.classList.add("show");
  fade.classList.add("show");
})

buttonExitModal.addEventListener('click', () => {
  modal.classList.remove("show");
  fade.classList.remove("show");
})

// controlar páginação do modal:

const buttonNextPage = document.querySelector("#button-next-page");
const buttonPreviousPage = document.querySelector("#button-previous-page");
const totalPages = document.querySelectorAll(".body-modal").length;

const slider = document.querySelector(".slider");
let currentStage = 0;

updateSliderPosition();

function updateSliderPosition() {
  const percentage = currentStage * -100;
  slider.style.transform = `translateX(${percentage}%)`;

  buttonNextPage.style.display = currentStage > 0 ? "Finalizar" : "Próximo";
  buttonPreviousPage.style.display = currentStage < totalPages - 2 ? "none" : "initial";
}

buttonNextPage.addEventListener("click", () => {
  if (currentStage < totalPages - 1) {
    try {
      if (validationData()) {
        processData();

        currentStage++;
        updateSliderPosition();
      }
    }
    catch (error) {
      console.log(error.message);
    }
  }
});

buttonPreviousPage.addEventListener("click", () => {
  if (currentStage > 0) {
      processData();

      currentStage--;
      updateSliderPosition();
    }
});

// validação dos dados:

function validationData() {
  const inputs = document.querySelectorAll(".form-flex input");

  inputs.forEach(input => {
    if (input.value == null || input.value == undefined || input.value == "") {
      buttonNextPage.removeEventListener('click', () => {});
      throw new Error("Preencha todos os dados.");
    }
  });

  return true;
}

function getDataFormPage1() {
  const nome = document.querySelector("#nome-usuario").value;
  const sobrenome = document.querySelector("#sobrenome-usuario").value;
  const nascimento = document.querySelector("#nascimento-usuario").value;
  const email = document.querySelector("#email-usuario").value;
  const telefone = document.querySelector("#telefone-usuario").value;
  const endereco = document.querySelector("#endereco-usuario").value;


}

function getDataFormPage2() {

}

function getDataFormPage3() {

}

function processData() {
  if (currentStage == 0) {
    getDataFormPage1();
    console.log("processa page 1");
  }
  else if (currentStage == 1) {
    console.log("processa page 2");
    getDataFormPage2();
  }
  else if (currentStage == 2) {
    getDataFormPage3();
  }
}
