namespace java com.navercorp.pinpoint.thrift.dto

struct TAttackInfo {
  1: string agentId
  2: string applicationName
  3: i64 time
  4: string attacks

  5: optional string rpc
  6: optional string host
  7: optional string transactionId
  8: optional i64 spanId
}

  
