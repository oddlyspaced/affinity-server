package oddlyspaced.surge.data

import kotlinx.serialization.Serializable

@Serializable
data class NodeData(val name: String)

@Serializable
data class Node(val id: String, val data: NodeData)

val nodes = mutableListOf<Node>()