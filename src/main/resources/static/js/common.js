const alertMsg = document.getElementById("alert")?.dataset.errorMsg;

if (alertMsg?.trim()) {
    alert(alertMsg);
}