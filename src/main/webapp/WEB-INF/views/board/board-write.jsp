<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        .write-container {
            width: 50%;
            margin: 200px auto 150px;
            font-size: 1.2em;
        }

        .fileDrop {
            width: 600px;
            height: 200px;
            border: 1px dashed gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 1.5em;
        }

        .uploaded-list {
            display: flex;
        }

        .img-sizing {
            display: block;
            width: 100px;
            height: 100px;
        }
    </style>
</head>

<body>
    <div class="wrap">
        <%@ include file="../include/header.jsp" %>

        <div class="write-container">

            <form id="write-form" action="/board/write" method="post" autocomplete="off" enctype="multipart/form-data">

                

                <div class="mb-3">
                    <label for="writer-input" class="form-label">작성자</label>
                    <input type="text" class="form-control" id="writer-input" placeholder="이름" name="writer"
                        maxlength="20" readonly value="${loginUser.name}">    <%-- 강제로 로그인한 사람의 아이디가 적히게 된다 --%>

                </div>
                <div class="mb-3">
                    <label for="title-input" class="form-label">글제목</label>
                    <input type="text" class="form-control" id="title-input" placeholder="제목" name="title">
                </div>
                <div class="mb-3">
                    <label for="exampleFormControlTextarea1" class="form-label">내용</label>
                    <textarea name="content" class="form-control" id="exampleFormControlTextarea1" rows="10"></textarea>
                </div>

                <!-- 첨부파일 드래그 앤 드롭 영역 -->
                <div class="form-group">
                    <div class="fileDrop">
                        <span>Drop Here!!</span>
                    </div>
                    <div class="uploadDiv">
                        <input type="file" name="files" id="ajax-file" style="display:none;">
                    </div>
                    <!-- 업로드된 파일의 썸네일을 보여줄 영역 -->
                    <div class="uploaded-list">

                    </div>
                </div>


                <div class="d-grid gap-2">
                    <button id="reg-btn" class="btn btn-dark" type="button">글 작성하기</button>
                    <button id="to-list" class="btn btn-warning" type="button">목록으로</button>
                </div>

            </form>

        </div>

        <%@ include file="../include/footer.jsp" %>



    </div>


    <script>
        // 게시물 등록 입력값 검증 함수
        function validateFormValue() {
            // 이름입력태그, 제목 입력태그
            const $writerInput = document.getElementById('writer-input');
            const $titleInput = document.getElementById('title-input');
            let flag = false; // 입력 제대로하면 true로 변경

            console.log('w: ', $writerInput.value);
            console.log('t: ', $titleInput.value);

            if ($writerInput.value.trim() === '') {
                alert('작성자는 필수값입니다~');
            } else if ($titleInput.value.trim() === '') {
                alert('제목은 필수값입니다~');
            } else {
                flag = true;
            }

            console.log('flag:', flag);

            return flag;
        }

        // 게시물 입력값 검증
        const $regBtn = document.getElementById('reg-btn');

        $regBtn.onclick = e => {
            // 입력값을 제대로 채우지 않았는지 확인
            if (!validateFormValue()) {
                return;
            }

            // 필수 입력값을 잘 채웠으면 폼을 서브밋한다.
            const $form = document.getElementById('write-form');
            $form.submit();
        };


        //목록버튼 이벤트
        const $toList = document.getElementById('to-list');
        $toList.onclick = e => {
            location.href = '/board/list';
        };
    </script>

    <script>
        // start JQuery 
        $(document).ready(function () {

            function isImageFile(originFileName) {
                //정규표현식
                const pattern = /jpg$|gif$|png$/i;
                return originFileName.match(pattern);
            }

            // 파일의 확장자에 따른 렌더링 처리
            function checkExtType(fileName) {

                //원본 파일 명 추출
                let originFileName = fileName.substring(fileName.indexOf("_") + 1);


                // hidden input을 만들어서 변환파일명을 서버로 넘김
                const $hiddenInput = document.createElement('input');
                $hiddenInput.setAttribute('type', 'hidden');
                $hiddenInput.setAttribute('name', 'fileNames');
                $hiddenInput.setAttribute('value', fileName);

                $('#write-form').append($hiddenInput);

                //확장자 추출후 이미지인지까지 확인
                if (isImageFile(originFileName)) { // 파일이 이미지라면

                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');
                    $img.setAttribute('src', '/loadFile?fileName=' + fileName);
                    $img.setAttribute('alt', originFileName);
                    $('.uploaded-list').append($img);
                }

                // 이미지가 아니라면 다운로드 링크를 생성
                else {

                    const $a = document.createElement('a');
                    $a.setAttribute('href', '/loadFile?fileName=' + fileName);

                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');
                    $img.setAttribute('src', '/img/file_icon.jpg');
                    $img.setAttribute('alt', originFileName);

                    $a.append($img);
                    $a.innerHTML += '<span>' + originFileName + '</span';

                    $('.uploaded-list').append($a);

                }


            }


            // 드롭한 파일을 화면에 보여주는 함수
            function showFileData(fileNames) {

                // 이미지인지? 이미지가 아닌지에 따라 구분하여 처리
                // 이미지면 썸네일을 렌더링하고 아니면 다운로드 링크를 렌더링한다.
                for (let fileName of fileNames) {
                    checkExtType(fileName);
                }
            }



            // drag & drop 이벤트
            const $dropBox = $('.fileDrop');

            // drag 진입 이벤트
            $dropBox.on('dragover dragenter', e => {
                e.preventDefault();
                $dropBox
                    .css('border-color', 'red')
                    .css('background', 'lightgray');
            });

            // drag 탈출 이벤트
            $dropBox.on('dragleave', e => {
                e.preventDefault();
                $dropBox
                    .css('border-color', 'gray')
                    .css('background', 'transparent');
            });

            // drop 이벤트
            $dropBox.on('drop', e => {
                e.preventDefault();
                // console.log('드롭 이벤트 작동!');

                // 드롭된 파일 정보를 서버로 전송

                // 1. 드롭된 파일 데이터 읽기
                // console.log(e);
                const files = e.originalEvent.dataTransfer.files;
                // console.log('drop file data: ', files);

                // 2. 읽은 파일 데이터를 input[type=file]태그에 저장
                const $fileInput = $('#ajax-file');
                $fileInput.prop('files', files);

                // console.log($fileInput);

                // 3. 파일 데이터를 비동기 전송하기 위해서는 FormData객체가 필요
                const formData = new FormData();

                // 4. 전송할 파일들을 전부 FormData안에 포장
                for (let file of $fileInput[0].files) {
                    formData.append('files', file);
                }

                // 5. 비동기 요청 전송
                const reqInfo = {
                    method: 'POST',
                    body: formData
                };
                fetch('/ajax-upload', reqInfo)
                    .then(res => {
                        //console.log(res.status);
                        return res.json();
                    })
                    .then(fileNames => {
                        console.log(fileNames);

                        showFileData(fileNames);
                    });

            });

        });
        // end jQuery
    </script>


</body>

</html>