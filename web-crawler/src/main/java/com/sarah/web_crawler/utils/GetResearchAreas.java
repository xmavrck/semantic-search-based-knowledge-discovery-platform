package com.sarah.web_crawler.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GetResearchAreas {

	public List<String> getRelatedAreas(String researchArea) {
		List<String> result = new ArrayList<>();
		switch (researchArea) {

		case "Data_Structures":
			result.addAll(Arrays.asList(new String[] { "Databases", "Hierarchical", "Graph Databases", "SQL",
					"Relational", "Non-Relational", "NoSQL" }));
			;
			break;
		case "High_performance_Computing":
			result.addAll(Arrays.asList(new String[] { "Cloud Computing", "Cluster Computing", "Grid Computing",
					"Load Sharing and Balancing" }));
			break;
		case "Human_computer_interaction":
			result.addAll(Arrays.asList(new String[] { "Cognitive Ergonomics", "Cognitive Task Design",
					"Computer Supported Collaborative Work", "Human Factors", "Interactive Design",
					"Interactive System Designs", "Software Product Design", "Usability Engineering",
					"User Centered Design", "User Experience Design", "User Interface Design", "Human Architecture",
					"Man Machine Interface", "Information Architecture" }));
			break;
		case "Natural_Language_Processing":
			result.add("Generic Steps");
			result.add("Disclosure Integration");
			result.add("Lexical Analysis");
			result.add("Pragmatic Analysis");
			result.add("Semantic Analysis");
			result.add("Syntactic Analysis");
			result.add("NLP Types");
			result.add("Natural Language Detection");
			result.add("Sentence Planning");
			result.add("Text Planning");
			result.add("Text Realization");
			result.add("Natural Language Understanding");
			result.add("Difficulties");
			result.add("Lexical Ambiguity");
			result.add("Referential Ambiguity");
			result.add("Syntax Level Ambiguity");
			result.add("Terminology");
			result.add("Pattern Recognition");
			result.add("Robotics");
			break;
		case "Networking":
			result.add("Layers");
			result.add("Application Layer");
			result.add("Data Link Layer");
			result.add("Network Layer");
			result.add("Routing Algorithms");
			result.add("Bellman Ford Algorithm");
			result.add("Dijkastra Algorithm");
			result.add("The Floyyd Warshall Algorithm");
			result.add("Routing Types");
			result.add("Adaptive Routing");
			result.add("Centralized");
			result.add("Distributed");
			result.add("Isolated");
			result.add("Backward Learning");
			result.add("Hot Potato");
			result.add("Delta Routing");
			result.add("Hierarchical Routing");
			result.add("Multipath Routing");
			result.add("Non-Hierarchical Routing");
			result.add("Non-Adaptive Routing");
			result.add("Flooding");
			result.add("Random Walk");
			result.add("Policy Based Routing");
			result.add("Shortest Path Routing");
			result.add("Source Routing");
			result.add("Physical Layer");
			result.add("Bandwidth");
			result.add("Communication Links");
			result.add("Full Duplex");
			result.add("Half Duplex");
			result.add("Multipoint");
			result.add("Point to Point");
			result.add("Simplex");
			result.add("Transmission Media");
			result.add("Guided Transmission Media");
			result.add("Coaxicle Cable");
			result.add("Optical Fibre");
			result.add("Twisted Pair");
			result.add("Shielded Twisted Pair");
			result.add("Wireless Transmission Media");
			result.add("Radio");
			result.add("Satellite Communication");
			result.add("Terrestial Microwave");
			result.add("Presentation Layer");
			result.add("Session Layer");
			result.add("Transport Layer");
			result.add("Protocols");
			result.add("Address Return Protocol");
			result.add("Internet Control Message Protocol");
			result.add("Interet Protocol");
			result.add("Reverse Address Return Protocol");
			result.add("Transport Layer Protocol");
			result.add("User Diagram Protocol");
			result.add("Wireless Networks");
			result.add("Architecture");
			result.add("Ad-Hoc Network");
			result.add("Infrastructure Network");
			result.add("WATM");
			result.add("WLAN");
			break;
		case "Mobile_and_sensor_systems":
			result.add("Mobile Sensors");
			result.add("Energy Saving");
			result.add("Local Communication Among Phones");
			result.add("Security And Privacy");
			result.add("Sensing Fusing And Learning");
			result.add("Mobile System Key Goals");
			result.add("Wireless Sensing Networks");
			break;

		case "Algorithms_and_Complexity":
			result.add("Algorithms");
			result.add("Analysis");
			result.add("Searching");
			result.add("Sorting");
			break;
		case "Artificial_intelligence_and_machine_learning":
			result.add("Computer Visison");
			result.add("Data Mining and Modelling");
			result.add("Image Processing");
			result.add("Knowledge Representation");
			result.add("Natural Language Processing");
			result.add("Pattern Recognition");
			result.add("Robotics");
			result.add("Generic Steps");
			result.add("Disclosure Integration");
			result.add("Lexical Analysis");
			result.add("Pragmatic Analysis");
			result.add("Semantic Analysis");
			result.add("Syntactic Analysis");
			result.add("NLP Types");
			result.add("Natural Language Detection");
			result.add("Sentence Planning");
			result.add("Text Planning");
			result.add("Text Realization");
			result.add("Natural Language Understanding");
			result.add("Difficulties");
			result.add("Lexical Ambiguity");
			result.add("Referential Ambiguity");
			result.add("Syntax Level Ambiguity");
			result.add("Terminology");
			result.add("Pattern Recognition");
			result.add("Robotics");
			break;
		case "Computational_Biology":
			break;
		case "Computer_Architecture_and_Design":
			break;
		case "Computer_Graphics_and_Visualization":
			break;
		case "Computer_vision":
			result.add("High Level Vision");
			result.add("Intermediate Level Vision");
			result.add("Low Level Vision");
			result.add("Analysis");
			break;
		case "Data_mining_and_modeling":
			result.add("Data Cleaning");
			result.add("Data Processing");
			result.add("Model_Building");
			break;
		case "Embedded_systems":
			result.add("Connected");
			result.add("Hw-Sw System");
			result.add("Memory");
			result.add("Microprocessor based");
			result.add("Reactive and realtime");
			result.add("Single Functioned");
			result.add("Tightly Constrained");
			break;
		case "Multimedia_Computing":
			result.add("Animation");
			result.add("Audio");
			result.add("Graphics");
			result.add("Text");
			result.add("Images");
			result.add("Video");
			break;
		case "Distributed_Systems_and_Parallel_computing":
			result.add("Client Server System");
			result.add("Message Passing");
			result.add("Modularity");
			result.add("Peer to Peer System");
			result.add("Distributed Memory");
			result.add("Parallel Computing");
			result.add("Distributed Memory");
			result.add("Shared Memory");
			result.add("Hybrid Distributed Shared Memory");
			break;
		case "Operating_Systems":
			result.add("Operating System Types");
			result.add("Batch Operating System");
			result.add("Distributed Operating System");
			result.add("Real time Operating System");
			result.add("Hard Real time Operating System");
			result.add("Soft time Operating System");
			result.add("Batch Operating System");
			result.add("Network Operating System");
			result.add("Time Sharing Operating System");
			result.add("Services");
			result.add("Communication");
			result.add("Error Handling");
			result.add("File System Intereobality");
			result.add("IO Operation");
			result.add("Program Execution");
			result.add("Protection");
			result.add("Resource Management");
			break;
		case "Programming_languages":
			result.add("C");
			result.add("Java");
			result.add("C++");
			result.add("C-Sharp");
			result.add("Closure");
			result.add("Scala");
			result.add("Python");
			break;
		case "Security-_Privacy_and_Cryptography":
			result.add("CryptoSystems");
			result.add("Components of CryptoSystems");
			result.add("CipherText");
			result.add("Decryption Algorithm");
			result.add("Decryption Key");
			result.add("Encryption Algorithm");
			result.add("Encryption Key");
			result.add("Plain Text");
			result.add("Cryptographic Attacks");
			result.add("Birthday Attack");
			result.add("Brute Force Attack");
			result.add("Choosen Plain Text Attack");
			result.add("Cipher Text Only Attack");
			result.add("Dictionary Attacks");
			result.add("Fault Analysis Attack");
			result.add("Known Plain Text Attack");
			result.add("Man in Middle Attack");
			result.add("Power Analysis Attack");
			result.add("Side Channel Attack");
			result.add("Timing Attack");
			result.add("Types of CryptoGraphic Systems");
			result.add("Assymetric Key Encryption");
			result.add("Symetric Key Encryption");
			result.add("Security Key Systems");
			result.add("Private Key Systems");
			result.add("3DES");
			result.add("DES");
			result.add("Public Key Systems");
			result.add("Galois Field System");
			result.add("Knapsack Problem System");
			break;
		case "Software_Engineering":
			result.add("Software Design");
			result.add("Architectural Design");
			result.add("Function Oriented Design");
			result.add("Object Oriented Design");
			result.add("Detailed Design");
			result.add("High Level Design");
			result.add("Software Life Cycle Models");
			result.add("Classic Waterfall Model");
			result.add("Evolutionary Model");
			result.add("Iterative Waterfall Model");
			result.add("Prototyping Model");
			result.add("Spiral Model");
			break;
		default:
			break;
		}
		return result;
	}
}
