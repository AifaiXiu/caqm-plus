/*
 Navicat Premium Dump SQL

 Source Server         : caqm
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41-0ubuntu0.24.10.1)
 Source Host           : 192.168.186.128:3306
 Source Schema         : caqm-plus

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41-0ubuntu0.24.10.1)
 File Encoding         : 65001

 Date: 12/04/2025 20:42:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for audit
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `airport_id` bigint NOT NULL,
  `audit_method_id` bigint NOT NULL,
  `checklists_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `close_user_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  `findings_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `main_auditor_id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `other_auditors_id` bigint NOT NULL,
  `planned_end_date` datetime(6) NULL DEFAULT NULL,
  `planned_start_date` datetime(6) NULL DEFAULT NULL,
  `process_id` bigint NOT NULL,
  `real_end_date` datetime(6) NULL DEFAULT NULL,
  `real_start_date` datetime(6) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NOT NULL,
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for checklist
-- ----------------------------
DROP TABLE IF EXISTS `checklist`;
CREATE TABLE `checklist`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `checklist_items_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dept_id` bigint NOT NULL,
  `files_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dataitem
-- ----------------------------
DROP TABLE IF EXISTS `dataitem`;
CREATE TABLE `dataitem`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `auditors_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checklist_item_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checklist_item_files_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checklist_item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checklist_item_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checklist_items_files_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checklist_note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `finding_id` bigint NOT NULL,
  `status` int NOT NULL,
  `type` int NOT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for finding
-- ----------------------------
DROP TABLE IF EXISTS `finding`;
CREATE TABLE `finding`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `airport_id` bigint NOT NULL,
  `audit_id` bigint NOT NULL,
  `close_user_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `evaruate_duty_man_id` bigint NOT NULL,
  `evaruate_result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `event_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `execute_date` datetime(6) NULL DEFAULT NULL,
  `finding_type_id` bigint NOT NULL,
  `finish_date` datetime(6) NULL DEFAULT NULL,
  `finisher_id` bigint NOT NULL,
  `is_secure` int NOT NULL,
  `measure_duty_man_id` bigint NOT NULL,
  `measure_type` int NOT NULL,
  `possibility` int NOT NULL,
  `process_id` bigint NOT NULL,
  `related_dept_id` bigint NOT NULL,
  `riskvarue` int NOT NULL,
  `root_analyze_id` bigint NOT NULL,
  `severity` int NOT NULL,
  `status` int NOT NULL,
  `target_close_time` datetime(6) NULL DEFAULT NULL,
  `target_date` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
