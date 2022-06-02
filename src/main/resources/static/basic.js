let targetId;

$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    getPosts();
})

function getPosts() {
    $('#Chosen-One').empty();
    $("#Chosen-One-Comment").empty()
    $('#cards-box').empty();
    $.ajax({
        type: "GET",
        url: "/list",
        data: {},
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let posts = response[i];
                let id = posts['id'];
                let title = posts['title']
                let username = posts['username'];
                let content = posts['content'];
                let createdAt = posts['createdAt']
                addHTML(id, title, username, content, createdAt);
            }
        }
    });
}

function addHTML(id, title, username, content, createdAt) {
    let tempHtml = makeMessage(id, title, username, content, createdAt);
    $('#cards-box').append(tempHtml);
}

function makeMessage(id, title, username, content, createdAt, i) {
    return `<div id="${id}-candidate"><div class="card">
                        <!-- date/username 영역 -->
                        <div class="metadata">
                            <div class="date">
                                ${createdAt}
                            </div>
                            <div id="${id}-username" class="username">
                                ${username}  님의 게시글
                            </div>
                            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="" onclick="editPost('${id}')" style="display: none">
                            <img id="${id}-close" class="icon-close-post" src="images/X.png" alt="" onclick="closePost('${id}')" style="display: none">
                        </div>
                        <div>
                            <h3 id="${id}-title">${title}</h3>
                        </div>
                        <div>
                            <button class="btnInside" onclick="showPost('${id}')" id="${id}-buttonIn">내용보기</button>
                        </div>
                        <!-- contents 조회/수정 영역-->
                        <div class="contents">
                            <div class="metadata" style="display: none" id="{id}-tguide">제목 수정하기</div>
                            <textarea class="ti-edit" name="" id="${id}-titlearea" cols="30" rows="10" style="display: none"></textarea>
                            <div id="${id}-contents" class="text" style="display: none">
                                ${content}
                            </div>
                            <div class="metadata" style="display: none" id="{id}-cguide">내용 수정하기</div>
                            <div id="${id}-editarea" class="edit">
                                <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
                            </div>
                             <!-- 버튼 영역-->
                            <div class="footer">
                                <div>
                                <textarea class="passcheck" placeholder="비밀번호 입력" name="passwordcheck" id="${id}-check" cols="30"
                                    rows="10" style="margin-bottom: 10px; display: none"></textarea>
                                </div>

                                <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="" onclick="deleteOne('${id}')" style="display: none">
                                <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="" onclick="submitEdit('${id}')" >

                            </div>
                        </div>
                    </div>
                    <div class="area-write" id="${id}-commentArea">
                    <textarea class="commentField" placeholder="댓글은 여기에 달아주세요" id="${id}-comment" cols="30"
                    rows="3" style="display: none"></textarea><img id="${id}-submitComment" style="display: none; margin-bottom: 9px"  src="images/comment.png" alt="" onclick="submitComment('${id}')" ></div>
                    </div>`;
}

function writePost() {
    if ($.cookie('token')) {
        let title = $('#title').val();
        let content = $('#content').val();

        if (title == ''){return alert("제목은 필수입니다");}
        if (content == ''){return alert("내용입력은 필수입니다");}
        if (content.trim().length > 140) {return alert('공백 포함 140자 이하로 입력해주세요');}
        let data = {'title':title, 'content': content};

        $.ajax({
            type: "POST",
            url: "/post",
            headers:{'Authorization': $.cookie('token')},
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                window.location.reload();
            }
        });

        $('#title').hide();
        $('#username').hide();
        $('#password').hide();
        $('#content').hide();
        $('#writeImg').hide();
        $('#closeposter').hide();
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function showPost(id) {
    let candidate = $(`#${id}-candidate`).clone();
    $('#Chosen-One').append(candidate)
    $("#cards-box").empty();


    $(`#${id}-close`).show();
    $(`#${id}-edit`).show();
    $(`#${id}-contents`).show();

    $(`#${id}-buttonIn`).hide();
    $(`#${id}-comment`).show();
    $(`#${id}-submitComment`).show();
    $('#Chosen-One-Comment').empty();
    $.ajax({
        type: "GET",
        url: `/comment/list/${id}`,
        success: function (response) {
            console.log(response)
            for (let i = 0; i < response.length; i++) {
                let comments = response[i];
                let username = comments['username']
                let comment = comments['comment']
                let createdAt = comments['createdAt']
                let commentid = comments['id']
                console.log(username,comment,createdAt)

                let temp_html = `<div class="commentCard">
                                            <div class="metadata">
                                                <div class="date">
                                                    ${createdAt}
                                                </div>
                                                <div id="${commentid}-username" class="username">
                                                    ${username}  님의 댓글
                                                </div>
                                            </div>
                                             <div style="padding: 0 23px 0 10px;" id="${commentid}-commain">${comment}
                                             <img id="${commentid}-deletecom" style="float: right; margin 0px 10px 0px 10px" src="images/delete.png" alt="" onclick="deleteComment('${commentid}','${username}')">
                                             <img id="${commentid}-comedit" style="float: right; margin-right: 10px" src="images/edit.png" alt="" onclick="showEditComment('${commentid}','${comment}')">
                                            </div>
                                            <div style="padding: 10px 23px 0 10px">
                                             <textarea id="${commentid}-commentarea" class="comEdit" name="" id="" cols="30" rows="3" style="display: none"></textarea>
                                             <img id="${commentid}-submitcomE" src="images/done.png" alt="" style="float: right; margin-top: 10px; display: none" onclick="submitEditCom('${commentid}','${username}','${id}')" >

                                             </div>

                                        </div>`

                $('#Chosen-One-Comment').append(temp_html)
            }

        }
    });

}

function closePost(id) {
    $(`#${id}-edit`).hide();
    $(`#${id}-close`).hide();
    $(`#${id}-submit`).hide();
    $(`#${id}-delete`).hide();
    $(`#${id}-check`).hide();
    $(`#${id}-editarea`).hide();
    $(`#${id}-contents`).hide();
    $(`#${id}-cguide`).hide();
    $(`#${id}-tguide`).hide();
    $(`#${id}-titlearea`).hide();
    $(`#${id}-title`).show();
    $(`#${id}-buttonIn`).show();
    $(`#${id}-comment`).hide();
    $(`#${id}-submitComment`).hide();
    $('#Chosen-One').empty();
    $('#Chosen-One-Comment').empty();
    window.location.reload()
}

function editPost(id) {
    if ($.cookie('token')) {
        $(`#${id}-edit`).hide();
        $(`#${id}-contents`).hide();
        $(`#${id}-submit`).show();
        $(`#${id}-delete`).show();
        $(`#${id}-check`).show();
        $(`#${id}-cguide`).show();
        $(`#${id}-tguide`).show();
        let contents = $(`#${id}-contents`).text().trim();
        $(`#${id}-textarea`).val(contents);
        let title = $(`#${id}-title`).text().trim();
        $(`#${id}-titlearea`).val(title);
        $(`#${id}-titlearea`).show();
        $(`#${id}-title`).hide();
        $(`#${id}-editarea`).show();
        $(`#${id}-comment`).hide();
        $(`#${id}-submitComment`).hide();
        $('#Chosen-One-Comment').empty();
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function submitEdit(id) {
    if ($.cookie('token')) {
        let title = $(`#${id}-titlearea`).val().trim();
        let content = $(`#${id}-textarea`).val().trim();

        if(title == '') {alert("타이틀은 비워둘 수 없습니다"); return;}
        if(content == '') {alert("수정할 내용을 작성해주세요"); return;}

        let data = {'title':title, 'content': content};

        alert(data)

        $.ajax({
            type: "GET",
            url: `/mod/${id}`,
            headers:{'Authorization': $.cookie('token')},
            success: function (response) {
                if (response) {
                    alert(response)
                    $.ajax({
                        type: "PUT",
                        url: `/mod/${id}`,
                        headers: {"Authorization": token},
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        success: function (response) {
                            alert('JOY문을 수정하였습니다.');
                            window.location.reload();
                        }
                    });
                } else {alert("작성자만 글을 수정할 수 있습니다")}
            }
        })
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function deleteOne(id) {
    if ($.cookie('token')) {
        $.ajax({
            type: "GET",
            url: `/mod/${id}`,
            headers:{'Authorization': $.cookie('token')},
            success: function (response) {
                if (response) {
                    $.ajax({
                        type: "DELETE",
                        url: `/mod/${id}`,
                        headers: {"Authorization": token},
                        success: function (response) {
                            alert('JOY문을 삭제하였습니다.');
                            window.location.reload();
                        }
                    })
                } else {alert("작성자만 게시글을 삭제할 수 있습니다")}
            }
        })
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function submitComment(id) {
    if ($.cookie('token')) {
        let comment = $(`#${id}-comment`).val();
        if (comment == ""){alert("내용입력은 필수입니다"); return;}
        if (comment.trim().length > 80) {alert('공백 포함 80자 이하로 입력해주세요'); return;}

        let data = {'comment': comment};

        $.ajax({
            type: "POST",
            url: `/comment/${id}`,
            headers:{'Authorization': $.cookie('token')},
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
            }
        });

        getPosts()
        setTimeout(function(){showPost(id);},100)
        showPost(id)
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function deleteComment(id, username){

    if ($.cookie('token')) {
        $.ajax({
            type: "GET",
            url: "/post/user",
            headers:{'Authorization': $.cookie('token')},
            data: {username:username},
            success: function (response) {

                if (response) {
                    $.ajax({
                        type: "DELETE",
                        url: `/comment/${id}`,
                        headers:{'Authorization': $.cookie('token')},
                        data: {},
                        success: function (response) {

                            getPosts()
                            setTimeout(function(){showPost(response);},100)

                        }
                    });
                    alert("정상처리 되었습니다")
                }
            }
        });
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function showPostBox() {
    $('#cards-box').empty();
    $('#Chosen-One').empty();
    $('#Chosen-One-Comment').empty();
    $('#username').show();
    $('#password').show();
    $('#title').show();
    $('#content').show();
    $('#writeImg').show();
    $('#closeposter').show();
}

function showEditComment(id,comment) {
    if ($.cookie('token')) {
        $(`#${id}-commentarea`).show();
        $(`#${id}-submitcomE`).show();
        let commentApply = comment.trim();
        $(`#${id}-commentarea`).val(commentApply);
        $(`#${id}-commain`).hide();
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}
function submitEditCom(id, username, postNum) {
    if ($.cookie('token')) {
        let comment = $(`#${id}-commentarea`).val().trim()
        if (comment.length > 80) {alert("코멘트는 80자 미만으로 작성해주세요"); return;}
        if (comment == "") {alert("수정할 내용을 입력해 주세요"); return;}
        let data = {'postNum':postNum, 'username':username, 'comment':comment}

        $.ajax({
            type: "GET",
            url: "/post/user",
            headers:{'Authorization': $.cookie('token')},
            data: {username:username},
            success: function (response) {

                if (response) {
                    $.ajax({
                        type: "PUT",
                        url: `/comment/${id}`,
                        headers:{'Authorization': $.cookie('token')},
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        success: function (response) {

                            getPosts()
                            setTimeout(function(){showPost(response);},100)

                        }
                    });
                    alert("정상처리 되었습니다")
                }
            }
        });
    } else {
        alert("로그인이 필요한 서비스입니다")
        window.location.href = '/user/loginView';
    }
}

function logout() {
    if ($.cookie('token')) {
        $.ajaxSetup({
            headers:{
                'Authorization': $.cookie('token', "", { path: '/' })
            }
        });
        $.ajaxSetup({
            headers:{
                'Authorization': $.cookie('token', "", { path: '/user' })
            }
        });
        alert("애도를 마치고 로그아웃 합니다")
    } else {
        alert("로그인되어 있지 않습니다")
    }
}

function test() {
    alert("개발용 테스트 아이콘입니다")
    data = {}
    $.ajax({
        type: "POST",
        url: '/test',
        headers:{'Authorization': $.cookie('token')},
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
        }
    });

}