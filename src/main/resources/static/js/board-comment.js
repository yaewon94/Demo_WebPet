const errorMsgPanel = document.getElementById("error-msg");

// 댓글 등록
const addForm = document.getElementById("board-comment-add-form");

addForm.addEventListener("submit", async (e) => {

    e.preventDefault();
    const formData = new FormData(addForm);
    const comment = Object.fromEntries(formData.entries());
    const response = await postJson("/board/comment/add", comment);

    if (!response.ok) {
        const errors = await response.json();
        errorMsgPanel.textContent = Object.values(errors)[0]; // map의 첫번째 오류 메세지 출력
    }else{
        console.log("add comment success");
    }
});