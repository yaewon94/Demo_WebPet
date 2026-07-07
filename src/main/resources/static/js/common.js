// alert
const alertMsg = document.getElementById("alert")?.dataset.errorMsg;

if (alertMsg?.trim()) {
    alert(alertMsg);
}

// html
function hideForm(form, parentElementName, elementName){
    const commentDiv = form.closest(parentElementName);
    commentDiv.querySelector(elementName).innerHTML = "";
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
    const query = new URLSearchParams(params).toString();
    const requestUrl = query ? `${url}?${query}` : url;
    return fetch(requestUrl, {
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

async function processSubmit(dataOrForm, url, errorMsgHTML=null){
    let data;
    if (dataOrForm instanceof HTMLFormElement) {
        data = Object.fromEntries(new FormData(dataOrForm).entries());
    } else {
        data = dataOrForm;
    }
    const response = await postJson(url, data);

    if(response == null) return;
    if (!response.ok) {
        const errorRes = await response.json();
        const errorType = errorRes.type;
        if(errorType === "VALIDATION_ERROR"){
            if(errorMsgHTML){
                errorMsgHTML.textContent =
                    errorRes.errorMessages?.[0] ?? "입력값 오류";
            }
        }else if(errorType === "ACCESS_DENIED"){
            alert(errorRes.errorMsg);
            location.href = errorRes.redirectUrl;
        }
        return false;
    }

    return true;
}