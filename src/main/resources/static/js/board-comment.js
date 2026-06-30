const boardType = document.getElementById("boardType").value;
const boardId = Number(document.getElementById("boardId").value);
const commentPaging = document.getElementById("comment-paging");

(async () => {
    try {
        await init();
    } catch (e) {
        console.error(e);
    }
})();

// 초기화
async function init() {
    // 댓글 등록 이벤트 초기화
    const addForm = document.getElementById("board-comment-add-form");
    addForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const formData = new FormData(addForm);
        const comment = Object.fromEntries(formData.entries());
        const response = await postJson("/board/comment/add", comment);

        if (!response.ok) {
            const errors = await response.json();
            document.getElementById("comment-write-error-msg").textContent = Object.values(errors)[0]; // map의 첫번째 오류 메세지 출력
            return;
        }

        await showCommentList();
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


// 댓글 리스트 가져오기
async function showCommentList(commentPage=0){

    const response = await getJson(
        `/board/comment/${boardType}/${boardId}`,
        { commentPage: commentPage });

    if(!response.ok){
        document.getElementById("comment-list-error-msg").textContent = "댓글 목록 가져오기를 실패했습니다";
        return;
    }

    const result = await response.json();
    createCommentListHTML(result.comments.content);
    createCommentPagingHTML(result.paging, result.comments.totalPages);
}

function createCommentListHTML(comments){
    const commentList = document.getElementById("comment-list");
    commentList.innerHTML = "";
    comments.forEach(comment => {
        commentList.innerHTML += `
        <div class="comment">
            <div>
                <span>${comment.userName}</span>
                <span>${comment.createdAt}</span>
            </div>
            <div>
                <span>${comment.content}</span>
            </div>
        </div>
    `;
    })
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