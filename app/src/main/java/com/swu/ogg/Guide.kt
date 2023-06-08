package com.swu.ogg

import androidx.fragment.app.Fragment


/* 애플리케이션 설계 원칙
  1. 단일 책임 : 클래스 or 모듈 or 메서드는 단 하나의 기능을 가져야 함
  2. 개방-폐쇄 : 확장에 대해서는 열려 있어야 하고 수정에 대해서는 닫혀 있어야 함 -> 기능 추가 및 변경에 용이 해짐
  3. 리스코프 치환 : 자식 클래스는 부모 클래스로 치환될 수 있어야 함
  4. 인터페이스 분리 : 이용하지 않는 메서드에 의존 하지 않도록 인터페이스를 분리
  5. 의존 역전 : 상위 계층이 하위 계층에 의존하지 않게 모듈을 분리

  * 애플리케이션을 구성하는 컴포넌트
   - 액티비티
   - 프래그먼트
   - 서비스
   - 브로드캐스트 리시버
   - 콘텐츠 프로바이더

  * 안드로이드의 컴포넌트
   - 컴포넌트의 생명 주기는 안드로이드 시스템이 제어함
   - 데이터 및 상태에 대한 내용을 컴포넌트에 저장하면 안됨
   - 전화, 알림, 메모리 부족 등으로 작업이 방해 받거나 중단될 수 있으므로 예외 사항 처리 필요

  ※ 참고
   - SNS : 암시적 인텐트로 카메라 애플리케이션을 호출하여 사진을 찍고 결과를 가지고 SNS로 돌아감
   - 쇼핑몰 : 결제 시 카드앱을 실행하고 결제 후 정보를 가지고 쇼핑몰로 돌아감
*/

/* 클린 아키텍쳐란?
  : 소프트웨어의 관심사를 계층 별로 분리 -> 코드 종속성이 외부->내부로 의존, 내부->외부가 되어선 안됨
               ㄴ어떤 상태나 데이터에 영향을 미치는 정보의 집합

   (외부) UI -> Presenters -> Usecases -> Entities (내부)

  * Entities
   - 비즈니스 규칙 캡슐화
   - 네트워크나 DB 관련 클래스
   - 애플리케이션 관련 코드는 포함하면 안됨

  * Use Cases
   - 애플리케이션 관련 비즈니스 규칙 포함
   - 시스템의 모든 Usecase 구현체를 캡슐화
   - Entities로부터 데이터 흐름을 관리 하고 Usecases의 목적을 달성 하도록 함
   - 관심사를 분리하여 Entities에 영향을 미치지 않고
   - UI나 프레임워크 등 외부 계층에도 영향을 받지 않음
   - Model : DB의 query, 네트워크 요청 등의 비즈니스 로직 수행
   - Repository : 내부 DB 접근 혹은 저장, 원격 서버에 데이터 요청 -> 인터페이스를 구현 하여 외부 계층의 연결을 느슨하게 함
   - Executor : Repository나 Model 관련 작업이 백그라운드에서 수행할 수 있도록 작업 스레드를 관리 하고 제공함

  *  Interface Adapters
   - Usecases나 Entities로부터 얻은 데이터를 가공하는 계층
   - UI에서 얻은 데이터를 내부 DB나 원격 서버에 전송할 때도 이 계층에서 데이터를 가공하여 전달함
   - 비즈니스 로직과 프레임워크 코드를 자연스럽게 연결하는 역할을 함
   - Presenters, View, ViewModel, Controller 등

  * Frameworks & Drivers
   - UI와 관련된 액티비티, 프래그먼트, 인텐트 전달 및 DB, 콘텐츠 프로바이더, 네트워크 프레임워크를 포함
*/

/*
 ※ 참고1) 관심사 분리를 통한 APP 설계
   - Activity, Fragment -> ViewModel만 참조
   - ViewModel -> Repository 참조, 데이터 변화를 감지할 수 있는 LiveData 형태로 관리
               -> 내부 DB만 참조, 클라이언트 DB와 서버 DB는 Request를 통해 비동기적 통기화
   - Repository -> 내부 모델(DB) 혹은 원격 모델(네트워크) 참조, 네트워크 데이터는 내부 DB에 저장, ViewModel에서 관리

  ※ 참고2) MVVM 디자인 : Model, View, ViewModel 측면으로 관심사 분리
   * Model
    - 비즈니스 로직과 데이터를 다루는 영역
    - DBMS에 의해 관리, 함수를 통해 데이터 제공 & 입력 & 수정

   * View
    - 사용자에게 표현되는 영역
    - Model로부터 얻은 데이터를 표현
    - Activity, Fragment가 그 역할을 함

   * ViewModel
    - View에 표현할 데이터를 Observable 타입으로 관리
    - View는 ViewModel에 구독 요청을 하여 화면을 표현
    - View와 ViewModel은 1:N 관계
    - Model에서 받은 데이터를 가공하여 Observable 타입 형태로 ViewModel에 저장
*/

class Guide : Fragment() {}