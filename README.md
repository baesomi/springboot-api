# springboot-api
spring boot 기반의 간단한 회원가입/로그인 api

# WEB API 명세서


URI 다음과 같은 형식을 따릅니다.    
~~~
/v1/{API종류}/{API이름}
~~~

## API 상세

|분류|설명|
|------|-----|
|API종류|각 API는 기능 영역별로 분류되어 제공됩니다.<br>- member : 사용자 정보를 다루는 API|
|API이름|기능 영역별로 분류된 API 종류 중에서 각 액션을 수행하는 API명을 지칭합니다.|


## API 리스트

|API 종류|API명|설명|
|------|-----|-----|
|member|join|회원가입|
||login|로그인|
||info|사용자 정보 조회|

## Member

### /member/join
회원가입

- **API 호출 방식**    

|메서드|요청URI|
|------|---|
|POST| /v1/member/join|


### Request

- **Request Header**    

|파라미터|타입|설명|
|------|---|---|
|Content-Type|String|application/json|

- **Request Elements**    

|파라미터|타입|필수여부|설명|
|------|---|---|---|
|id|String|필수|이메일 형식|
|password|String|필수|영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열|
|name|String|필수|사용자 이름|

### Response
- **Response Elements**    

|필드|타입|필수여부|설명|
|------|---|---|---|
|success|Boolean|필수|API 실행결과 <br> - API 호출 성공일 경우, **true** 값 반환 <br> - API 호출 실패일 경우, **false** 값 반환|
|errorCode|String|선택|API 실행 오류 정보 <br> - API 호출 실패(false)일 경우 제공|

- error code    

|코드|설명|
|------|---|
|2000|INVALID_PARAMETER : 파라미터는 제공되었지만, 해당 값이 올바르지 않습니다.|
|2010|DUPLICATED_MEMBER : 이미 동일한 정보의 사용자가 존재합니다.|


### /member/login
로그인

- **API 호출 방식**

|메서드|요청URI|
|------|---|
|POST| /v1/member/login|

### Request
- **Request Header**    

|파라미터|타입|설명|
|------|---|---|
|Content-Type|String|application/json|

- **Request Elements**    

|파라미터|타입|필수여부|설명|
|------|---|---|---|
|id|String|필수|이메일 형식|
|password|String|필수|영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열|


### Response

- **Response Elements**    

|필드|타입|필수여부|설명|
|------|---|---|---|
|success|Boolean|필수|API 실행결과 <br> - API 호출 성공일 경우, **true** 값 반환 <br> - API 호출 실패일 경우, **false** 값 반환|
|errorMsg|String|선택|API 실행 오류 정보 <br> - API 호출 실패(false)일 경우 제공|
|accessToken|String|필수|- 성공 시 정상적인 토큰 반환 <br> - 실패 시 빈 문자열 반환|

- error message    

|메시지|
|------|
|잘못된 비밀번호 입니다.|
|가입되지 않은 E-MAIL입니다.|


### member/info
로그인 사용자 정보 조회

- **API 호출 방식**

|메서드|요청URI|
|------|---|
|GET| /v1/member/info|

### Request
- **Request Header**    

|파라미터|타입|설명|
|------|---|---|
|Content-Type|String|application/json|
|X_PATH_TOKEN|String|로그인 성공 후 획득한 인증 토큰(Access Token)|


- **Request Elements**    
none    


### Response

- **Response Elements**      

|필드|타입|필수여부|설명|
|------|---|---|---|
|HTTPStatus|Boolean|필수|API 실행결과 <br> - API 호출 성공일 경우, **HTTPStatus.OK** 값 반환|
|member|Object[]|선택|멤버의 상세 정보 <br> - API 호출 성공(HTTPStatus.OK)일 경우 제공|

- member 정보    

|필드|타입|설명|
|------|---|---|
|id|String|이메일 형식|
|name|String|사용자 이름|
|lastLoginDate|String|직전 로그인 시간|

- HttpStatus Code    

|상태|설명|
|------|---|
|HttpStatus.UNAUTHORIZED|유효하지 않은 Token인 경우 반환|
|HttpStatus.NOT_FOUND|사용자를 찾을 수 없는 경우 반환|


