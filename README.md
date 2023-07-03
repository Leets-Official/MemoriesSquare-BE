# MemoriesSquare-BE

> 인생네컷 사진 공유 서비스

## Getting Started
```shell
git clone https://github.com/Leets-Official/MemoriesSquare-BE

chmod +x gradlew

./gradlew clean build

java -jar ./build/libs/memoriessquare-be-0.0.1-SNAPSHOT.jar
```

## Environment
```dotenv
# 포트 변경이 필요할 경우 사용 (기본값: 8080)
PORT=8080

# 데이터베이스 연결 정보
DATABASE_URL=jdbc:mariadb://localhost:3306/db
DATABASE_USERNAME=
DATABASE_PASSWORD=

# JWT 암호화 키
JWT_ACCESS_SECRET=
JWT_REFRESH_SECRET=

# CORS Origin 정보
CORS_ORIGIN_DEVELOPMENT=
CORS_ORIGIN_PRODUCTION=

# 소셜로그인 성공 시 리다이렉트 주소
SOCIAL_LOGIN_REDIRECT=

# 소셜로그인 API 정보
FACEBOOK_CLIENT_ID=
FACEBOOK_SECRET=
```

## Commit Convention
- [Udacity Convention](http://udacity.github.io/git-styleguide/)
