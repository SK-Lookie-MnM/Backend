# 🍀 MNM

## **Branch Rules**

1. **Main Branch**
    
    브랜치 이름 : Main 최종 배포가 가능한 상태만을 관리하는 브랜치입니다.
    
2. **Feature Branch**
    
    브랜치 이름 : Feature/<이슈번호>-<기능명> 브랜치 이름 예시 : Feature/1-login 기능 개발, 오류 수정 등에 사용되는 브랜치입니다

## ✔️ 커밋 컨벤션
커밋 메시지에 대한 약속.
커밋 메시지 구조는 크게 3가지로 나뉜다(제목, 본문, 꼬리말)

>type: Subject -> 제목  
(한칸 띄우기)  
body(생략 가능) -> 본문  
(한칸 띄우기)  
footer(생략 가능) -> 꼬리말  
각 커밋 메시지 구조에는 규칙이 존재한다.
아래에서 좋은 커밋 메시지를 만드는 규칙에 대해 언급하겠다.

ex)
>Feat: Add signin, signup <br>
회원가입 기능, 로그인 기능 추가(예시를 위해 간단히 작성)  
Resolves: #1

------------------

✏️ Commit Type<br>
**Type**	**설명**<br>
Feat:	새로운 기능 추가 <br>
Fix:	버그 수정 또는 typo <br>
Refactor:	리팩토링 <br>
Design:	CSS 등 사용자 UI 디자인 변경 <br>
Comment:	필요한 주석 추가 및 변경<br>
Style:	코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우<br>
Test:	테스트(테스트 코드 추가, 수정, 삭제, 비즈니스 로직에 변경이 없는 경우)<br>
Chore:	위에 걸리지 않는 기타 변경사항(빌드 스크립트 수정, assets image, 패키지 매니저 등)<br>
Init:	프로젝트 초기 생성<br>
Rename:	파일 혹은 폴더명 수정하거나 옮기는 경우<br>
Remove:	파일을 삭제하는 작업만 수행하는 경우<br>

------------------

🐭 Subject Rule
1. 제목은 최대 50글자 넘지 않기
2. 마침표 및 특수기호 사용x
3. 첫 글자 대문자, 명령문 사용
4. 개조식 구문으로 작성(간결하고 요점적인 서술)

------------------

Body Rule
1. 한 줄당 72자 내로 작성
2. 최대한 상세히 작성
3. 어떻게 보다는 '무엇을', '왜' 변경했는지에 대해 작성

------------------

🐮 Footer Rule
1. 유형: #이슈 번호의 형식으로 작성
2. 이슈 트래커 ID를 작성
3. 여러개의 이슈 번호는 ,로 구분
4. 이슈 트래커 유형은 아래와 같다

Issue Tracker	설명
Fixes:	이슈 수정중(아직 해결되지 않은 경우)
Resolves:	이슈를 해결한 경우
Ref:	참조할 이슈가 있을 때 사용
Related to:	해당 커밋에 관련된 이슈 번호(아직 해결되지 않은 경우)
ex) Footer에 Fixes: #1 이라고 작성하고 commit을 할 경우, 자동으로 issue #1과 매칭이 된다.
또한, Resolves: #1으로 이슈를 해결했다고 명시하면 그 이슈는 사라진다.한 번 써보면 감이 온다
