DROP TABLE if exists method_info;
CREATE TABLE method_info(
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  method_name VARBINARY(50) COMMENT '方法名',
  session_id VARBINARY(50) COMMENT 'sessionId',
  user_name VARBINARY(30) COMMENT '用户名',
  start_time BIGINT COMMENT '方法开始时间',
  end_time BIGINT COMMENT '方法结束时间',
  consumer_time BIGINT COMMENT '消耗时间',
  error_msg VARBINARY(255) COMMENT '方法异常',
  parent_id BIGINT COMMENT '父节点id,主要监控当前方法时候,内部方法的parentId是当前方法的id',
  create_time DATETIME COMMENT '创建时间'
)