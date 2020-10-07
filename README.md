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

### member/join
회원가입

- **API 호출 방식**    

|메서드|요청URI|
|------|---|
|POST| /v1/member/join|


- **Request Header**    

- **Request Elements**    

- **Response**  

### member/login
로그인

- **API 호출 방식**

|메서드|요청URI|
|------|---|
|POST| /v1/member/login|


- **Request Header**    

- **Request Elements**    

- **Response**    


### member/info
로그인 사용자 정보 조회

- **API 호출 방식**

|메서드|요청URI|
|------|---|
|GET| /v1/member/info|


- **Request Header**    

- **Request Elements**    

- **Response**    


