// 이미지 파일 유효성 검사
$(document).on('change', '#file', function() {
    let fileInput = $("#file")[0]; // 파일 입력 엘리먼트 가져오기
    let file = fileInput.files[0]; // 선택된 파일 가져오기
    if (!file) {
        // 파일이 선택되지 않았을 경우 처리
        return;
    }

    const isValidFile = /(\.jpg|\.jpeg|\.png|\.gif)$/i.exec(file.name);
    if (!isValidFile) {
        alert("허용되지 않는 파일 형식입니다. (jpg, jpeg, png, gif 파일만 첨부 가능합니다.)");
        fileInput.value = ""; // 파일 입력 필드 초기화
        const img = $("#preview-image");
        img.attr("src", "");
        return false;
    }

    if (file.size > 2 * 1024 * 1024) {
        alert("파일 크기는 최대 2MB까지 업로드 가능합니다.");
        fileInput.value = ""; // 파일 입력 필드 초기화
        const img = $("#preview-image");
        img.attr("src", "");
        return false;
    }
    previewImage(file);
});

// 미리보기창 설정
function previewImage(file) {
    const reader = new FileReader();
    reader.onload = function () {
        const img = $("#preview-image");
        img.attr("src", reader.result);
    };
    reader.readAsDataURL(file);
}