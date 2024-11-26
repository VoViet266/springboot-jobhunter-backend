CREATE TABLE "users" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "email" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  "username" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  "age" int NOT NULL,
  "address" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "created_at" datetime(6) DEFAULT NULL,
  "updated_at" datetime(6) DEFAULT NULL,
  "gender" enum('FEMALE','MALE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "password" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  "created_by" varchar(255) DEFAULT NULL,
  "refresh_token" mediumtext,
  "updated_by" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "role_id" bigint DEFAULT NULL,
  "company_id" bigint DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "FKin8gn4o1hpiwe6qe4ey7ykwq7" ("company_id"),
  KEY "FKp56c1712k691lhsyewcssf40f" ("role_id"),
  CONSTRAINT "FKin8gn4o1hpiwe6qe4ey7ykwq7" FOREIGN KEY ("company_id") REFERENCES "companies" ("id"),
  CONSTRAINT "FKp56c1712k691lhsyewcssf40f" FOREIGN KEY ("role_id") REFERENCES "roles" ("id")
);

CREATE TABLE "jobs" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "name" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "actived" bit(1) NOT NULL,
  "quantity" int NOT NULL,
  "location" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "salary" double DEFAULT NULL,
  "description" mediumtext,
  "company_id" bigint DEFAULT NULL,
  "lever" enum('JUNIOR','MIDDLE','SENIOR') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "created_at" datetime(6) DEFAULT NULL,
  "end_date" datetime(6) DEFAULT NULL,
  "start_date" datetime(6) DEFAULT NULL,
  "updated_at" datetime(6) DEFAULT NULL,
  "created_by" varchar(255) DEFAULT NULL,
  "updated_by" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "FKrtmqcrktb6s7xq8djbs2a2war" ("company_id"),
  CONSTRAINT "FKrtmqcrktb6s7xq8djbs2a2war" FOREIGN KEY ("company_id") REFERENCES "companies" ("id")
);
CREATE TABLE "skills" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "name" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "created_at" datetime(6) DEFAULT NULL,
  "updated_at" datetime(6) DEFAULT NULL,
  "created_by" varchar(255) DEFAULT NULL,
  "updated_by" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("id")
);
CREATE TABLE "roles" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "name" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "description" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "active" bit(1) NOT NULL,
  "created_at" datetime(6) DEFAULT NULL,
  "created_by" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "updated_at" datetime(6) DEFAULT NULL,
  "updated_by" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("id")
);
CREATE TABLE "permissions" (
  "created_at" datetime(6) DEFAULT NULL,
  "id" bigint NOT NULL AUTO_INCREMENT,
  "updated_at" datetime(6) DEFAULT NULL,
  "api_path" varchar(255) DEFAULT NULL,
  "created_by" varchar(255) DEFAULT NULL,
  "method" varchar(255) DEFAULT NULL,
  "module" varchar(255) DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "updated_by" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("id")
);

CREATE TABLE "companies" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "name" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  "address" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "description" mediumtext,
  "logo" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  "created_at" datetime(6) DEFAULT NULL,
  "updated_at" datetime(6) DEFAULT NULL,
  "created_by" varchar(255) DEFAULT NULL,
  "updated_by" varchar(255) DEFAULT NULL,
  PRIMARY KEY ("id")
);
CREATE TABLE "resumes" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "email" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  "status" tinyint DEFAULT NULL,
  "url" varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  "user_id" bigint DEFAULT NULL,
  "created_at" datetime(6) DEFAULT NULL,
  "updated_at" datetime(6) DEFAULT NULL,
  "job_id" bigint DEFAULT NULL,
  "created_by" varchar(255) DEFAULT NULL,
  "updated_by" varchar(255) DEFAULT NULL,
  "resumes_id" bigint DEFAULT NULL,
  "resumes11_id" bigint DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "FKjdec9qbp2blbpag6obwf0fmbd" ("job_id"),
  KEY "FKefm7gduxl00sldhyxac222797" ("resumes_id"),
  KEY "FKm62ewgfvd3xl09ha3al7o6k2r" ("resumes11_id"),
  KEY "FK340nuaivxiy99hslr3sdydfvv" ("user_id"),
  CONSTRAINT "FK340nuaivxiy99hslr3sdydfvv" FOREIGN KEY ("user_id") REFERENCES "users" ("id"),
  CONSTRAINT "FKjdec9qbp2blbpag6obwf0fmbd" FOREIGN KEY ("job_id") REFERENCES "jobs" ("id")
);
CREATE TABLE "job_skill" (
  "job_id" bigint NOT NULL,
  "skill_id" bigint NOT NULL,
  PRIMARY KEY ("job_id","skill_id"),
  KEY "skill_id" ("skill_id"),
  CONSTRAINT "job_skill_ibfk_1" FOREIGN KEY ("job_id") REFERENCES "jobs" ("id"),
  CONSTRAINT "job_skill_ibfk_2" FOREIGN KEY ("skill_id") REFERENCES "skills" ("id")
);
CREATE TABLE "permission_role" (
  "role_id" bigint NOT NULL,
  "permission_id" bigint NOT NULL,
  PRIMARY KEY ("role_id","permission_id"),
  UNIQUE KEY "UKt49nxpqax9cveurs8f61sns2d" ("role_id","permission_id"),
  KEY "permission_id" ("permission_id"),
  CONSTRAINT "permission_role_ibfk_1" FOREIGN KEY ("role_id") REFERENCES "roles" ("id"),
  CONSTRAINT "permission_role_ibfk_2" FOREIGN KEY ("permission_id") REFERENCES "permissions" ("id")
);