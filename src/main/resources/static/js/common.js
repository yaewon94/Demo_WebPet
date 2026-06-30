// alert
const alertMsg = document.getElementById("alert")?.dataset.errorMsg;

if (alertMsg?.trim()) {
    alert(alertMsg);
}
// fetch method
async function postJson(url, data) {
    return fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(data)
    });
}

async function getJson(url, params) {
    const query = new URLSearchParams(params);
    return fetch(`${url}?${query}`, {
        method: "GET"
    });
}