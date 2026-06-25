let totalImageSize = 0;

class UploadAdapter {

    constructor(loader) {
        this.loader = loader;
    }

    upload() {

        return this.loader.file
            .then(file => {

                // TEMP
                // TODO : 서버에서 계산하는 로직 작성
                if (totalImageSize + file.size > 20 * 1024 * 1024) {
                    throw new Error(
                        "이미지 총 용량은 20MB를 초과할 수 없습니다."
                    );
                }

                totalImageSize += file.size;

                const data = new FormData();
                data.append("file", file);//  @RequestParam 으로 받아오는 이름과 일치해야 함

                return fetch("/upload/image", {
                    method: "POST",
                    body: data,
                    headers: {
                        [csrfHeader]: csrfToken
                    }
                })
                    .then(async response => {

                        if (!response.ok) {
                            const errorMsg = await response.text();
                            alert(errorMsg);
                            console.log(errorMsg);
                            throw new Error(errorMsg);
                        }

                        return response.json();
                    })
                    .then(result => {

                        return {
                            default: result.imageUrl
                        };

                    });

            });

    }
}