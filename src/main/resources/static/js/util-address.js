const address1 = document.getElementById("address1");
const address2 = document.getElementById("address2");

(async () => {
    try {
        await init();
    } catch (e) {
        console.error(e);
    }
})();

// 초기화
async function init(){
    // 이벤트 초기화
    address1.addEventListener("change", async (e) => {
        await showDetailAddressSelection(address1.value);
    });

    // select box 초기화
    await initAddressSelectBox();
}

// 주소 select box 보여주기
async function initAddressSelectBox(){
    const response = await getJson(`/address`);

    if(response.ok){
        const result = await response.json();

        address1.innerHTML = "";
        address1.add(new Option("특별시/광역시/도", ""));
        result.address1.forEach(item => {
            address1.add(new Option(item.name, item.code));
        });
    }
}

async function showDetailAddressSelection(address1){
    if (!address1) {
        address2.innerHTML = "";
        address2.add(new Option("시/구", ""));
        return;
    }

    const response = await getJson(`/address`, {address1:address1});

    if(response.ok){
        const result = await response.json();

        address2.innerHTML = "";
        address2.add(new Option("시/구", ""));
        result.address2.forEach(item => {
            address2.add(new Option(item.name, item.code));
        });
    }
}