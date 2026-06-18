const errorMsg = document.getElementById("errorMsg");
const errorCode = document.getElementById("errorCode").value;
const shelterArea = document.getElementById("optional_display_shelter");
const isUserTypeChecked = document.querySelector('input[name="user_type"]:checked');

if (isUserTypeChecked) {
    updateUI(isUserTypeChecked.value);
}

function updateUI(userType) {
    shelterArea.classList.toggle("display_hidden", userType !== "SHELTER");
    clearShelterRelatedError(userType);
}

function clearShelterRelatedError(userType){
    if(userType !== "SHELTER" && errorCode === "SHELTER_REQUIRED") {
        errorMsg.innerText = "";
    }
}

document.querySelectorAll('input[name="user_type"]').forEach(radio => {
    radio.addEventListener("change", function () {
        updateUI(this.value);
    });
});