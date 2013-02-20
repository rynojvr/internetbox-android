package com.rynojvr.general.client.internetbox;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;
import com.rynojvr.general.client.internetbox.entities.Question;


public class Main {

	private final static String UrlString = "http://internetboxpodcast.com";

	public static void main(String[] args) throws Exception {
		// autoVote(args);
		// editPost();

		// main1();

		// System.out.println("done");

		// ChatTopic blah = new ChatTopic();
		// String json = new Gson().toJson(blah, ChatTopic.class);
		// System.out.println(json);

		// Document doc =
		// Jsoup.connect("http://ibscraper.herokuapp.com/chat/1946/0/25").get();

		// System.out.println(doc.text());

		// ChatTopicResponseWrapper topicRespnse = new
		// Gson().fromJson(doc.text(), ChatTopicResponseWrapper.class);
		// ChatTopic topic = new Gson().fromJson(doc.text(), ChatTopic.class);
		// ChatTopic topic = IBClient.getChatTopic(1946);
		// for (ChatReply reply : topic.getReplies()) {
		// System.out.println(reply);
		// System.out.println("ID: " + reply.getId());
		// System.out.println("Content: " + reply.getContent());
		// System.out.println("Created By: " + reply.getCreatedBy());
		// System.out.println();
		// }

//		 for (Question question : IBClient.getTopQuestions()) {
//		 System.out.println(question.getId());
//		 System.out.println(question.getCreatedBy());
//		 System.out.println(question.getContent());
//		 System.out.println(question.getRating());
//		 System.out.println();
//		 }
		
		for (ChatTopic topic : IBClient.getTopChatTopics()) {
			System.out.println(topic);
			System.out.println(topic.getContent());
			System.out.println();
		}

		// System.out.println(IBClient.getQuestion(4309));

		// Question question = new Question();
		// System.out.println(question);
		// System.out.println(new Gson().toJson(question));

//		 Question question = new
//		 Gson().fromJson("{id: 4309,created_by: \"Kershinator\",to_whom: \"Lindsay\",content: \"We've all heard about how Michael has dealt with people fronting on you, but do you have any stories about confronting people that were hitting on Michael?\",rating: 30,"+
//		 "best_pony: \"PinkiePie\",is_answered: false}", Question.class);
//		for (Question question : IBClient.getQuestions(0, 10000)) {
//			System.out.println(question);
//			System.out.println("TO: " + question.getToWhom());
//			System.out.println("FROM: " + question.getCreatedBy());
//			System.out.println("RATING: " + question.getRating());
//			System.out.println("CONTENT: " + question.getContent());
//			System.out.println();
//		}
	}

	public static void editPost() throws Exception {
		Document doc = Jsoup
				.connect("http://internetboxpodcast.com/chat/edit.php?id=30804")
				.data("body",
						"<i>Replying to <a href=\"#30794\">Mike</a>:</i> I missed it! D:")
				.userAgent("DashieFTW!").cookie("user", "50461c4e4f737").post();
	}

	// 50461c4e4f730

	/**
	 * Getting data from the home page
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main1() throws Exception {
		System.out.println("Start");

		Document doc = Jsoup.connect(UrlString).userAgent("DashieFTW!").get();
		Element ele = doc.select("article.socialContainer").first();
		Element title = ele.getElementsByTag("h5").first();
		Element content = ele.getElementsByTag("p").first();

		System.out.println(title.text());
		System.out.println(content.text());
	}

	/**
	 * Getting Chat topics.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main2(String[] args) throws IOException {
		System.out.println("Start");

		Document doc = Jsoup.connect(UrlString + "/chat/get-more.php/")
				.data("page", "1").userAgent("DashieFTW!")
				.followRedirects(true).get();
		Elements eles = doc.getElementsByClass("topicTitle");

		for (Element e : eles) {
			System.out.println(e.text());
		}
	}

	/**
	 * Getting Chat thread.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main3(String[] args) throws IOException {
		System.out.println("Start");

		Document doc = Jsoup.connect(UrlString + "/chat/topic.php")
				.data("id", "1403").userAgent("DashieFTW!").get();

		Element content = doc.getElementsByClass("chatContent").first();
		Element threadTitle = doc.getElementsByTag("h5").first();
		Element opUsername = doc.select("h4 > a").first();

		Elements replies = doc.getElementsByClass("reply");

		System.out.println(opUsername.text());
		System.out.println(threadTitle.text());
		System.out.println(content.text());
		System.out.println("--------------------------");

		for (Element e : replies) {
			Element replyUsername = e.select("h4 > a").first();
			Element replyContent = e.getElementsByClass("replyContent").first();

			System.out.println(replyUsername.text());
			System.out.println(replyContent.text());
			System.out.println();
		}
	}

	public static void autoVote(String[] args) {
		int numVotes = 9;
		final String questionID = "6942";

		for (int i = 0; i < numVotes; i++) {
			final int capture = i;
			new Thread(new Runnable() {
				public void run() {
					try {
						Document doc = Jsoup
								.connect(
										"http://questions.internetboxpodcast.com/rate.php")
								.data("id", questionID, "p", "applejack")
								.userAgent("DashieFTW!")
								.cookie("user", "61561c4e4f737" + capture)
								.get();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		}
	}
}
