import { openMessageFailure, openSpanDetails, closeMessageFailure, closeSpanDetails } from "../utils/messageFailure.js";
import { openMessageSuccess, closeMessageSuccess } from "../utils/messageSuccess.js";
import { setPerson, getPerson } from "../models/personModel.js";
import { PersonService } from "../service/personService.js";

const modal = document.querySelector(".modal-nova-conta");
const fade = document.querySelector(".fade");
const buttonOpenModal = document.querySelector("#button-open-modal");
const buttonExitModal = document.querySelector("#button-exit-modal");

function openModal() {
  modal.classList.add("show");
  fade.classList.add("show");
}

function closeModal() {
  modal.classList.remove("show");
  fade.classList.remove("show");
}

buttonOpenModal.addEventListener('click', openModal);
buttonExitModal.addEventListener('click', closeModal);

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

  buttonNextPage.style.display = currentStage === totalPages ? "Finalizar" : "Próximo";
  buttonPreviousPage.style.display = currentStage === 0 ? "none" : "initial";
}

buttonNextPage.addEventListener("click", () => {
    try {
      let dataValid;

      if (currentStage === 0) {
        dataValid = validationDataPage1();
      }
      else if (currentStage === 1) {
        dataValid = validationDataPage2();
      }
      else if (currentStage === 2) { 
        dataValid = validationDataPage3();

        if (dataValid) {
          console.log(getPerson());
          PersonService.cadastrar(getPerson());

          closeModal();
          openMessageSuccess("Usuário cadastrado!");
          currentStage = 0;
        }
      }

      if (dataValid) {
        processData();
  
        currentStage++;
        updateSliderPosition();
      }
    }
    catch (error) {
      openMessageFailure(error.message);
    }
});

buttonPreviousPage.addEventListener("click", () => {
  if (currentStage > 0) {
      processData();

      currentStage--;
      updateSliderPosition();
    
      closeMessageFailure();
      closeSpanDetails();
    }
});

// validação dos dados:

let errors = [];

const nomeInput = document.querySelector("#nome-usuario");
const sobrenomeInput = document.querySelector("#sobrenome-usuario");
const nascimentoInput = document.querySelector("#nascimento-usuario");
const emailInput = document.querySelector("#email-usuario");
const telefoneInput = document.querySelector("#telefone-usuario");
const sexoInput = document.querySelector("#sexo-usuario");
const enderecoInput = document.querySelector("#endereco-usuario");
const complementoInput = document.querySelector("#complemento-usuario");
const numeroInput = document.querySelector("#numero-usuario");
const nacionalidadeInput = document.querySelector("#nacionalidade-usuario");
const cpfInput = document.querySelector("#cpf-usuario");
const rgInput = document.querySelector("#rg-usuario");

const emailContaInput = document.querySelector("#email-usuario-conta");
const senhaContaInput = document.querySelector("#senha-usuario-conta");
const administradorContaInput = document.querySelector("#administrador-usuario-conta");

const inputsPage1 = document.querySelectorAll(".form-flex-page-1 input");
const inputsPage2 = document.querySelectorAll(".form-flex-page-2 input");
const inputsPage3 = document.querySelectorAll(".form-flex-page-3 input");

function validationDataPage1() {
  let numberErrors = 0;

  inputsPage1.forEach(error => {
    if (error.value == null || error.value == undefined || error.value == "") {
      numberErrors++;
    }
  });

  if (emailInput.value.toLowerCase().includes("@gmail.com")) {
    errors = errors.filter(error => error.type !== "email");
  }
  else {
    const errorExist = errors.some(error => error.type === "email");

    if (!errorExist) {
      errors.push({ type: "email", message: "Informe um e-mail válido", details: "O e-mail deve conter: @gmail.com" });
    }
  }

  if (telefoneInput.value.length > 11 || telefoneInput.value.length < 11) {
    const errorExistTelefone = errors.some(error => error.type === "telefone");

    if (!errorExistTelefone) {
      errors.push({ type: "telefone", message: "Informe telefone válido", details: "O telefone deve conter 11 dígitos" });
    }
  }
  else {
    errors = errors.filter(error => error.type !== "telefone");
  }

  if (numberErrors > 0) {
    throw new Error("Preencha todos os dados.");
  }

  if (errors.length > 0) {
    errors.forEach(error => {
      if (error.type == "email") {
        openSpanDetails(error.details);
        throw new Error(error.message);
      }
    })

    errors.forEach(error => {
      if (error.type == "telefone") {
        openSpanDetails(error.details);
        throw new Error(error.message);
      }
    })
  }
  else {
    closeMessageFailure();
    closeSpanDetails();
  }

  return true;
}

function validationDataPage2() {
  let numberErrors = 0;

  inputsPage2.forEach(error => {
    if (error.value == null || error.value == undefined || error.value == "") {
      numberErrors++;
    }
  });

  if (cpfInput.value.length > 11 || cpfInput.value.length < 11) {
    const errorExistCpf = errors.some(error => error.type === "cpf");

    if (!errorExistCpf) {
      errors.push({ type: "cpf", message: "Dígite um CPF válido.", details: "O CPF deve conter no mínino 11 dígitos." });
    }
  }
  else {
    errors = errors.filter(error => error.type !== "cpf");
  }

  if (rgInput.value.length > 9 || rgInput.value.length < 9) {
    const errorExistRg = errors.some(error => error.type === "rg");

    if (!errorExistRg) {
      errors.push({ type: "rg", message: "Dígite um RG válido.", details: "O RG deve conter no mínimo 9 dígitos." });
    }
  }
  else {
    errors = errors.filter(error => error.type !== "rg");
  }

  if (numberErrors > 0) {
    throw new Error("Preencha todos os dados.");
  }

  if (errors.length > 0) {
    errors.forEach(error => {
      if (error.type == "cpf") {
        openSpanDetails(error.details);
        throw new Error(error.message);
      }
    });

    errors.forEach(error => {
      if (error.type == "rg") {
        openSpanDetails(error.details);
        throw new Error(error.message);
      }
    });
  }
  else {
    closeMessageFailure();
    closeSpanDetails();
  }

  return true;
}

function validationDataPage3() {
  let numberErrors = 0;

  const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  inputsPage3.forEach(error => {
    if (error.value == null || error.value == undefined || error.value == "") {
      numberErrors++;
    }
  });

  if (emailContaInput.value.toLowerCase().includes("@gmail.com")) {
    errors = errors.filter(error => error.type !== "email-conta");
  }
  else {
    const errorExistEmailConta = errors.some(error => error.type === "email-conta");

    if (!errorExistEmailConta) {
      errors.push({ type: "email-conta", message: "Informe um e-mail válido", details: "O e-mail deve conter @gmail.com" });
    }
  }

  if (!regex.test(senhaContaInput.value)) {
    const errorExistSenhaConta = errors.some(error => error.type === "senha-conta");

    if (!errorExistSenhaConta) {
      errors.push({ 
        type: "senha-conta",
        message: "Dígite uma senha forte!", 
        details: "A senha deve conter 8 caracteres, <br> 1 letra maiúscula, <br> 1 número, <br> 1 caracter especial"
      });
    }
  }
  else {
    errors = errors.filter(error => error.type !== "senha-conta");
  }

  if (numberErrors > 0) {
    throw new Error("Preencha todas as informações.");
  }

  if (errors.length > 0) {
    errors.forEach(error => {
      if (error.type == "email-conta") {
        openSpanDetails(error.details);
        throw new Error(error.message);
      }
    });

    errors.forEach(error => {
      if (error.type == "senha-conta") {
        openSpanDetails(error.details);
        throw new Error(error.message);
      }
    })
  }
  else {
    closeMessageFailure();
    closeSpanDetails();
  }

  return true;
}

let user = {
  id: null,
  email: "",
  password: "",
  person: {
    id: null,
    firstName: "",
    lastName: "",
    birthDate: null,
    email: "",
    phone: "",
    gender: "",
    address: "",
    complement: "",
    number: "",
    nationality: "",
    cpf: "",
    rg: "",
  }
};

function getDataFormPage1() {
  user.person.firstName = nomeInput.value;
  user.person.lastName = sobrenomeInput.value;
  user.person.birthDate = nascimentoInput.value;
  user.person.email = emailInput.value;
  user.person.phone = telefoneInput.value;
  user.person.gender = sexoInput.value;

  setPerson(user);
}

function getDataFormPage2() {
  user.person.address = enderecoInput.value;
  user.person.complement = complementoInput.value;
  user.person.number = numeroInput.value;
  user.person.nationality = nacionalidadeInput.value;
  user.person.cpf = cpfInput.value;
  user.person.rg = rgInput.value;

  setPerson(user);
}

function getDataFormPage3() {
  user.email = emailContaInput.value,
  user.password = senhaContaInput.value;

  setPerson(user);
}

function processData() {
  if (currentStage == 0) {
    getDataFormPage1();
  }
  else if (currentStage == 1) {
    getDataFormPage2();
  }
  else {
    getDataFormPage3();
  }
}