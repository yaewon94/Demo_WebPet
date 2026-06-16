const errorMsg = document.getElementById("errorMsg");
const shelterArea = document.getElementById("optional_display_shelter");
const isUserTypeChecked = document.querySelector('input[name="user_type"]:checked');

if (isUserTypeChecked) {
    updateShelterUI(isUserTypeChecked.value);
}

function updateShelterUI(value) {
    if (value !== "SHELTER" && errorMsg) {
        errorMsg.innerText = "";
    }
    shelterArea.classList.toggle("display_hidden", value !== "SHELTER");
}

document.querySelectorAll('input[name="user_type"]').forEach(radio => {
    radio.addEventListener("change", function () {
        updateShelterUI(this.value);
    });
});