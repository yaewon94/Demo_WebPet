const errorMsg = document.getElementById("errorMsg")?.dataset.errorMsg;

if (errorMsg?.trim()) {
    alert(errorMsg);
}