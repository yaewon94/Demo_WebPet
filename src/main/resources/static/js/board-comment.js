// <th:block th:fragment="commentWritePanel"> 에 boardType, boardId 있음
const boardType = document.getElementById("boardType").value;
const boardId = Number(document.getElementById("boardId").value);
const commentPaging = document.getElementById("comment-paging");
let currentComments = []; // @ Page<CommentResponse>

(async () => {
    try {
        await init();
    } catch (e) {
        console.error(e);
    }
})();

// 초기화
async function init() {
    // submit 이벤트 초기화
    // e.preventDefault() => 브라우저에게 form 제출을 맡기지 않고, 개발자가 직접 js로 처리
    document.addEventListener("submit", async (e) => {
        // 댓글 등록
        if (e.target.matches("#board-comment-add-form")){
            e.preventDefault();
            await addComment(e.target);
        }
        // 댓글 수정
        if (e.target.classList.contains("comment-modify-form")) {
            e.preventDefault();
            await modifyComment(e.target);
        }
    });

    // 클릭 이벤트 초기화
    document.addEventListener("click", (e) => {
        // 댓글 수정
        if (e.target.classList.contains("comment-modify")) {
            const commentDiv = e.target.closest(".comment");
            const comment = currentComments.find(c => c.id == commentDiv.dataset.id);
            showModifyForm(comment);
        }
        // 댓글 수정 취소
        if (e.target.classList.contains("modify-cancel")){
            const commentDiv = e.target.closest(".comment");
            commentDiv.querySelector(".comment-modify-area").innerHTML = "";
        }
    });

    // 페이지 클릭 이벤트 초기화
    commentPaging.addEventListener("click", async (e) => {
        if (!e.target.classList.contains("comment-page")) {
            return;
        }
        e.preventDefault();

        await showCommentList(Number(e.target.dataset.page));
    });

    // 댓글 목록 보여주기
    await showCommentList();
}

// 댓글 등록
async function addComment(form){
    // @ CommentWriteRequest
    const comment = Object.fromEntries(new FormData(form).entries());
    const response = await postJson("/board/comment/add", comment);
    form.reset();

    if (!response.ok) {
        const errors = await response.json();
        document.getElementById("comment-write-error-msg").textContent = Object.values(errors)[0]; // map의 첫번째 오류 메세지 출력
        return;
    }

    await showCommentList();
}

// 댓글 리스트 가져오기
async function showCommentList(commentPage=0){
    // @ CommentListResponse (Page<BoardCommentResponse>, PagingResponse)
    const response = await getJson(
        `/board/comment/${boardType}/${boardId}`,
        { commentPage: commentPage });

    if(!response.ok){
        document.getElementById("comment-list-error-msg").textContent = "댓글 목록 가져오기를 실패했습니다";
        return;
    }

    const result = await response.json();
    currentComments = result.comments.content; // @ Page<BoardCommentResponse>
    createCommentListHTML();
    createCommentPagingHTML(result.paging, result.comments.totalPages);
}

function createCommentListHTML(){
    const commentList = document.getElementById("comment-list");
    let html = "";
    commentList.innerHTML = "";

    //@ Page<BoardCommentResponse>
    currentComments.forEach(comment => {
        html += `
        <div class="comment" data-id="${comment.id}">
            <div>
               <span>${comment.userName}</span>
               <span>${comment.createdAt}</span>
            </div>
            <div>
                <span>${comment.content}</span>
            </div>`;

        if (comment.canUpdate) {
            html += `
                <div>
                   <button class="comment-modify">수정</button>
                   <button>삭제</button>
                </div>
                <div class="comment-modify-area"></div>`;
        }

        html += `</div>`;
    })

    commentList.innerHTML += html;
}

// 댓글 페이징 처리
function createCommentPagingHTML(paging, totalPageCount){
    commentPaging.innerHTML = "";

    if (paging.startPage > 0) {
        commentPaging.innerHTML += `
        <a href="#" data-page="${paging.startPage - 1}" class="comment-page">이전</a>`;
    }

    for (let i = paging.startPage; i <= paging.endPage; i++) {
        commentPaging.innerHTML += `
        <a href="#" data-page="${i}" class="comment-page">${i + 1}</a>`;
    }

    if (paging.endPage < totalPageCount - 1) {
        commentPaging.innerHTML += `
        <a href="#" data-page="${paging.endPage + 1}" class="comment-page">다음</a>`;
    }
}

// 댓글 수정 form 보여주기
// @ param : comment (type : CommentResponse)
function showModifyForm(comment){
    const area = document.querySelector(
        `.comment[data-id="${comment.id}"] .comment-modify-area`
    );

    if (comment.isGuestComment) {
        area.innerHTML = `
        <form class="comment-modify-form" data-id="${comment.id}" 
            action="/board/comment/modify" method="post">
            <p class="error-msg comment-modify-error-msg"></p>
            <input type="hidden" name="commentId" value=${comment.id}>
            <input type="hidden" name="boardType" value=${boardType}>
            <input type="hidden" name="boardId" value=${boardId}>
            <div>
                <input name="userName" value="${comment.userName}">
                <input type="password" name="password" placeholder="비밀번호">
            </div>
            <textarea name="content">${comment.content}</textarea>
            <div>
                <button type="submit">저장</button>
                <button class="modify-cancel">취소</button>
            </div>
        </form>
        `;
    }else {
        area.innerHTML = `
        <form id="comment-modify-form">
            <textarea name="content">${comment.content}</textarea>
            <div>
                <button type="submit">저장</button>
                <button class="modify-cancel">취소</button>      
            </div>
        </form>
        `;
    }
}

// 댓글 수정
async function modifyComment(form){
    // @ CommentWriteRequest
    const comment = Object.fromEntries(new FormData(form).entries());
    const response = await postJson(`/board/comment/modify`, comment);

    if(response == null) return;
    if (!response.ok) {
        const errorRes = await response.json();
        const errorType = errorRes.type;
        if(errorType === "VALIDATION_ERROR"){
            form.querySelector(".comment-modify-error-msg").textContent =
                errorRes.errorMessages?.[0] ?? "입력값 오류";
        }else if(errorType === "ACCESS_DENIED"){
            alert(errorRes.errorMsg);
            location.href = errorRes.redirectUrl;
        }
        return;
    }

    await showCommentList();
}