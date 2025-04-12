package com.travelexperts.travelexpertsadmin.data.api.requests

// data/api/request/RegisterAgentRequest.kt
data class RegisterAgentRequest(
    val agent: AgentPayload,
    val password: String
)

data class AgentPayload(
    val agtfirstname: String,
    val agtmiddleinitial: String?,
    val agtlastname: String,
    val agtbusphone: String,
    val agtemail: String,
    val agtposition: String,
    val status: String = "approved",
    val role: String = "agent",
    val agencyid: AgencyId
)

data class AgencyId(
    val id: Int
)

