
function spreadCommentFromServer(bno) {
    console.log(bno);
    return $.ajax({
        url: "/comments",
        type: "GET",
        data: {
            bno: bno
        },
        dataType: "json"
    });
}


// 로컬 sql 댓글 작성 

function getCommentList(bno,loginId) {
    spreadCommentFromServer(bno)
        .done(function(result) {
            let $div = $("#Comment");
            $div.empty();
            let html = "<ul>";
            for (let i = 0; i < result.length; i++) {
                html += "<li>";
                html += `<div class="d-flex">`;
                if(result[i].cno !== result[i].pcno) {
                    html += `<div style="font-size: 20px;">ㄴ</div>`;
                }
                const FilePath = result[i].image_file ? result[i].image_file.replace(/\\/g, "/") : "no-image.png";
                html += `<div class="flex-shrink-0"><img class="rounded-circle" src="/fileUpload/${FilePath}" alt="프로필 이미지" /></div>`;
                html += `<div class="ms-3" style="width: 700px">`;
                html += `<div class="fw-bold" id="cmtWriter1" data-pcno="${result[i].pcno}" data-cno="${result[i].cno}" data-bno="${result[i].bno}">${result[i].writer}</div>`;
                html += `${result[i].content}`;
                html += `<div STYLE="text-align: right">`;
                if(result[i].cno === result[i].pcno){
                    html +=  `<button type="button" id="cmtReplyBtn" class="btn btn-sm btn-outline-primary ">답글</button>`;
                }
                if (result[i].writer === loginId) {
                    html += `<button type="button" id="cmtDelBtn" class="btn btn-sm btn-outline-danger ">삭제</button>`;
                }
                html += `</div>`;
                html += `</div></div><br></li><hr>`;
            }
            html += "</ul>";
            $div.append(html);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus + ": " + errorThrown);
        });
}

// azure 댓글 작성

// function getCommentList(bno,loginId) {
//     spreadCommentFromServer(bno)
//         .done(function(result) {
//             let $div = $("#Comment");
//             $div.empty();
//             let html = "<ul>";
//             for (let i = 0; i < result.length; i++) {
//                 html += "<li>";
//                 html += `<div class="d-flex">`;
//                 if(result[i].cno !== result[i].pcno) {
//                     html += `<div style="font-size: 20px;">ㄴ</div>`;
//                 }
//                 const FileName = result[i].image_file ? result[i].image_file.replace(/\\/g, "/") : "no-image.png";
//                 const ImageUrl = `https://youngmokfile.blob.core.windows.net/youngmokboard/${FileName}`;
//                 html += `<div class="flex-shrink-0"><img class="rounded-circle" STYLE="height: 75px; width: 75px;" src="${ImageUrl}" alt="프로필 이미지" /></div>`;
//                 html += `<div class="ms-3" style="width: 700px">`;
//                 html += `<div class="fw-bold" id="cmtWriter1" data-pcno="${result[i].pcno}" data-cno="${result[i].cno}" data-bno="${result[i].bno}">${result[i].writer}</div>`;
//                 html += `${result[i].content}`;
//                 html += `<div STYLE="text-align: right">`;
//                 if(result[i].cno === result[i].pcno){
//                     html +=  `<button type="button" id="cmtReplyBtn" class="btn btn-sm btn-outline-primary ">답글</button>`;
//                 }
//                 if (result[i].writer === loginId) {
//                     html += `<button type="button" id="cmtDelBtn" class="btn btn-sm btn-outline-danger ">삭제</button>`;
//                 }
//                 html += `</div>`;
//                 html += `</div></div><br></li><hr>`;
//             }
//             html += "</ul>";
//             $div.append(html);
//         })
//         .fail(function(jqXHR, textStatus, errorThrown) {
//             console.log(textStatus + ": " + errorThrown);
//         });
// }



async function postCommentToServer(Comment) {
    console.log(Comment);
    try {
        const url = '/comments';
        const config = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data: JSON.stringify(Comment)
        };
        return await $.ajax(url, config);
    } catch (error) {
        console.log(error);
    }
}


$('#cmtPostBtn').on('click', async () => {
    $('#replyForm').appendTo('body');
    const cmtText = $('#cmtText').val();
    console.log(cmtText);
    if (cmtText == null || cmtText === '') {
        alert('댓글을 입력해주세요');
        $('#cmtText').focus();
        return false;
    } else {
        let Comment = {
            bno: bnoVal,
            writer: $('#cmtWriter').text(),
            content: cmtText
        };
        console.log(Comment);
        const result = await postCommentToServer(Comment);
        if (result > 0) {
            alert('댓글등록성공');
        }
        await getCommentList(bnoVal, login);
        $('#cmtText').val('');
    }
});

$("#Comment").on("click", "#cmtReplyBtn", function() {
    // 1 replyForm을 옮기고
    $(this).parent().append($("#replyForm"));
    // 2 답글을 입력할 폼을 보여줌
    $("#replyForm").show();
});

$("#Comment").on("click","#cmtDelBtn",function(){
    if(!confirm("댓글을 삭제하시겠습니까?")){
        alert("댓글삭제를 취소하였습니다.");
        return false;
    }
    $("#replyForm").appendTo("body");

    const cno = $(this).closest('.d-flex').find('.fw-bold').data('cno');

    $.ajax({
        type:'DELETE',       // 요청 메서드
        url: '/comments/'+cno+'?bno='+bnoVal,  // 요청 URI
        //  생략시 자동으로 json
        success : function(result){
            alert(result);
            getCommentList(bnoVal,login);
        },
        error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()
});


$("#cmtReplybtn").click(function(){

    let Comment = {
        content : $("#replyText").val(),
        bno : bnoVal,
        pcno : $(this).closest('.d-flex').find('.fw-bold').data('cno')
    };

    console.log(Comment);

    if(Comment.content.trim()===''){
        alert("댓글을 입력해주세요");
        $("#replyText").focus()
        return;
    }

    $.ajax({
        type:'POST',       // 요청 메서드
        url: '/comments', // 요청 URI ?bno='+bno
        headers : { "content-type": "application/json"}, // 요청 헤더
        data : JSON.stringify(Comment),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(result){
            alert(result);
            getCommentList(bnoVal,login);
        },
        error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()

    $("#replyText").val('');
    $("#replyForm").hide();
    $("#replyForm").appendTo("body");

});

function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function () {
        const img = document.getElementById("preview-image");
        img.src = reader.result;
    };
    reader.readAsDataURL(input.files[0]);
}
