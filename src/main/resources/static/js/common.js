// alert
const alertMsg = document.getElementById("alert")?.dataset.errorMsg;

if (alertMsg?.trim()) {
    alert(alertMsg);
}
// fetch method
async function postJson(url, data) {
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken, // csrf (spring security)
            "X-Requested-With": "XMLHttpRequest" // AJAX 예외처리 용도
        },
        body: JSON.stringify(data)
    });

    if (!(await handleApiResponse(response))) {
        return null;
    }

    return response;
}

async function getJson(url, params) {
    const query = new URLSearchParams(params);
    return fetch(`${url}?${query}`, {
        method: "GET"
    });
}

async function handleApiResponse(response) {

    if (response.status === 401) {
        const res = await response.json();
        if(res.message.toString().trim().length > 0) alert(res.message);
        location.href = "/user/login";
        return false;
    }

    if (response.status === 403) {
        const res = await response.json();
        if(res.message.toString().trim().length > 0) alert(res.message);
        return false;
    }

    return true;
}