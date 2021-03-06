package com.rynojvr.general.client.internetbox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.rynojvr.android.internetbox.ui.AddAccountActivity;
import com.rynojvr.general.client.internetbox.entities.ChatReply;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;
import com.rynojvr.general.client.internetbox.entities.Episode;
import com.rynojvr.general.client.internetbox.entities.Question;
import com.rynojvr.general.client.internetbox.entities.Link;
import com.rynojvr.general.client.internetbox.entities.enums.Cast;


public class IBClient {
	
	private static String endpointDestination = "http://ibscraper.herokuapp.com";
//	private static String chatTopic
	private static Gson gson = new Gson();
	
	private static List<Episode> episodes = new ArrayList<Episode>() {{
		add(new Episode() {{
			id = 71;
			description = "The Internet Box is Gothic this week!";
			audioUrl = "http://traffic.libsyn.com/internetbox/InternetBoxEpisode71.mp3";
			cast = new ArrayList<Cast>() {{
				add(Cast.Ray);
				add(Cast.Andrew);
				add(Cast.Michael);
				add(Cast.Lindsay);
			}};
			linkDump = new ArrayList<Link>() {{
				add (new Link() {{
					text = "Thank you Filip Stumpy!";
					url = "http://achievementhunter.com/filipstumpy";
				}});
				add (new Link() {{
					text = "Dungeon Dice Monsters";
					url = "http://yugioh.wikia.com/wiki/Dungeon_Dice_Monsters";
				}});
				add (new Link() {{
					text = "Bandit Keith";
					url = "http://yugioh.wikia.com/wiki/Keith_Howard";
				}});
				add (new Link() {{
					text = "PAX East 2013";
					url = "http://east.paxsite.com/";
				}});
				add (new Link() {{
					text = "1080 Snowboarding";
					url = "https://www.youtube.com/watch?v=y8XNes9qOXs";
				}});
				add (new Link() {{
					text = "Special Poetry Slam – Werewolf";
					url = "https://www.youtube.com/watch?v=1XALVTzMOeQ";
				}});
				add (new Link() {{
					text = "Andrew’s Lock Screen";
					url = "http://i33.photobucket.com/albums/d82/B1GnBr0wN/IMG_8867_zps6fdc6d8d.png";
				}});
				add (new Link() {{
					text = "His Convo with Trevor";
					url = "http://i33.photobucket.com/albums/d82/B1GnBr0wN/IMG_3397_zps612cfd33.png";
				}});
				add (new Link() {{
					text = "The Fort";
					url = "https://twitter.com/theFORTatx";
				}});
				add (new Link() {{
					text = "Fern Gully";
					url = "http://en.wikipedia.org/wiki/Fern_Gully";
				}});
				add (new Link() {{
					text = "Action League Now";
					url = "http://en.wikipedia.org/wiki/Action_League_Now!";
				}});
				add (new Link() {{
					text = "Stan Lee";
					url = "http://en.wikipedia.org/wiki/Stan_Lee";
				}});
			}};
		}});
		add(new Episode() {{
			id = 70;
			description = "The Internet Box tastes the rainbow this week!";
			audioUrl = "http://traffic.libsyn.com/internetbox/InternetBoxEpisode70.mp3";
			cast = new ArrayList<Cast>() {{
				add(Cast.Ray);
				add(Cast.Andrew);
				add(Cast.Mike);
				add(Cast.Kerry);
			}};
			linkDump = new ArrayList<Link>() {{
				add (new Link() {{
					text = "Banned Skittles Commercial";
					url = "http://www.youtube.com/watch?v=n7EGjaxhTWs";
				}});
				add (new Link() {{
					text = "L.A. Noire Gag Reel";
					url = "http://www.youtube.com/watch?v=1va8FxBl7Cg";
				}});
				add (new Link() {{
					text = "Country of Offline";
					url = "http://roosterteeth.com/groups/profile.php?id=15041";
				}});
				add (new Link() {{
					text = "The Comedy Button";
					url = "http://www.geekbox.net/archives/2013/02/01/the-comedy-button-episode-63/";
				}});
				add (new Link() {{
					text = "@TheComedyButton";
					url = "https://twitter.com/TheComedyButton";
				}});
				add (new Link() {{
					text = "Andrew’s “Lotion”";
					url = "https://twitter.com/AH_MisterSir/status/297476666752659456";
				}});
				add (new Link() {{
					text = "Khum Dhan";
					url = "http://www.youtube.com/watch?v=7MRwayFwxRg";
				}});
				add (new Link() {{
					text = "AH WWE LP";
					url = "http://www.youtube.com/watch?v=lMYh_ylHMDs";
				}});
				add (new Link() {{
					text = "A Simple Walk AMA";
					url = "http://www.reddit.com/r/IAmA/comments/17rfxs/we_work_at_rooster_teeth_the_company_behind_red/";
				}});
				add (new Link() {{
					text = "A Simple Walk to Mordor 5";
					url = "http://roosterteeth.com/archive/?id=6801";
				}});
				add (new Link() {{
					text = "@Barbara88p";
					url = "https://twitter.com/Barbara88p";
				}});
				add (new Link() {{
					text = "Bill Nye";
					url = "http://en.wikipedia.org/wiki/Bill_Nye_the_Science_Guy";
				}});
			}};
		}});
	}};
	
	public static boolean sendReply(int topicId, String message, Map<String, String> userdata) {
		try {
			Document doc = Jsoup.connect("http://internetboxpodcast.com/chat/post-reply.php?id=" + topicId)
				.userAgent("Dashie FTW!")
				.data("content", message)
				.cookies(userdata)
				.post();
			
			Log.d("IB", "Send Reply response: " + doc.toString());
		} catch (IOException e) {
			Log.d("IB", "sendReply[" + topicId + "] error: " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * Attempts to sign in using the provided credentials. If there is an error
	 * then null is returned
	 * 
	 * @param username
	 * @param password
	 * @return - A map containing 'k' and 'user', or null sign-in failed.
	 * @throws IOException
	 */
	public static Map<String, String> signIn(String username, String password) {
		org.jsoup.Connection.Response response;
		try {
			response = HttpConnection
					.connect("http://internetboxpodcast.com/sign-in.php")
					.userAgent("Dashie FTW!")
					.data("return", "http://internetboxpodcast.com")
					.data("username", username).data("password", password)
					.method(Method.POST).execute();
		} catch (Exception e) {
			Log.d("IB", e.toString());
			return null;
		}
		
		Map<String, String> result = response.cookies();
		if (result.containsKey("k") && result.containsKey("user")) {
			Log.d("IBClient", "K: " + result.get("k"));
			Log.d("IBClient", "User: " + result.get("user"));
//			isLoggedIn(result);
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * Requests the home page of IB, and checks if the user identified by
	 * 'accountInfo' is logged in.
	 * 
	 * @param accountInfo
	 *            - A map containing 'k' and 'user'
	 * @return 
	 * 		Whether the provided credentials are logged in. 
	 */
	public static boolean isLoggedIn(Map<String, String> accountInfo) {
		Document doc;
		try {
			doc = Jsoup.connect("http://internetboxpodcast.com")
					.userAgent("Dashie FTW!")
					.cookies(accountInfo)
					.get();
			
			Log.d("IBClient", "Isloggedin: " + doc.html());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		Element ele = doc.select("#topRight > .m-btn-group > [class='m-btn orange']").first();
		Log.d("IBClient", "isLoggedIn[ele]: " + ele);
		boolean result = (ele != null && ele.text().length() != 0);
		Log.d("IBClient", "result: " + result);
//		Log.d("IBClient", "isLoggedIn[ele.text()]: " + ele.text());
		return result;
	}
	
	/**
	 * Returns the first 10 Chat Topics, ordered by most recently modified.
	 * 
	 * @return
	 * @throws IOException 
	 */
	public static List<ChatTopic> getTopChatTopics() throws IOException {
		return getChatTopics(0, 10);
	}
	
	/**
	 * Returns the Chat Topics from startIndex to endIndex
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws IOException
	 */
	public static List<ChatTopic> getChatTopics(int startIndex, int endIndex) throws IOException {
		Document doc = Jsoup.connect(endpointDestination + "/chat/" + startIndex + "/" + endIndex).get();
		String jsonString = doc.text();
		System.out.println(jsonString);
		ChatTopicResponseWrapper wrapper = gson.fromJson(jsonString, ChatTopicResponseWrapper.class);
		return wrapper.chatTopics;
	}
	
	/**
	 * Returns the Chat Topic with the given ID, and up to the first 25 replies.
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static ChatTopic getChatTopic(int id) throws IOException {
		Document doc = Jsoup.connect(endpointDestination + "/chat/" + id).get();
		String jsonString = doc.text();
		Log.d("IB CHAT TOPIC", jsonString);
		ChatTopic topic = gson.fromJson(jsonString, ChatTopic.class);
		return topic;
	}
	
	public static List<Episode> getEpisodes(int startIndex, int endIndex) throws IOException {
//		return episodes;
		
		Document doc = Jsoup.connect(endpointDestination + "/episode/" + startIndex + "/" + endIndex).get();
		String jsonString = doc.text();
		List<Episode> episodes = gson.fromJson(jsonString, EpisodeResponseWrapper.class).episodes;
		return episodes;
	}
	
	/**
	 * Gets the replies for the Chat Topic with the given id, starting from
	 * 'startIndex', to 'endIndex'.
	 * 
	 * @param id
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws IOException
	 */
	public static List<ChatReply> getChatTopicReplies(int id, int startIndex, int endIndex) throws IOException {
		Document doc = Jsoup.connect(endpointDestination + "/chat/" + id + "/" + startIndex + "/" + endIndex).get();
		String jsonString = doc.text();
		ChatTopic topic = gson.fromJson(jsonString, ChatTopic.class);
		return topic.getReplies();
	}
	
	public static Question getQuestion(int id) throws IOException {
		Document doc = Jsoup.connect(endpointDestination + "/question/" + id).get();
		String jsonString = doc.text();
//		System.out.println(jsonString);
		Question question = gson.fromJson(jsonString, Question.class);
		return question;
	}
	
	public static List<Question> getTopQuestions() throws IOException {
		return getQuestions(0, 10);
	}
	
	/**
	 * Returns a list of top rated questions from startIndex to endIndex
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws IOException
	 */
	public static List<Question> getQuestions(int startIndex, int endIndex) throws IOException {
		Document doc = Jsoup.connect(endpointDestination + "/question/top/" + startIndex + "/" + endIndex).get();
		String jsonString = doc.text();
		System.out.println(jsonString);
		QuestionResponseWrapper wrapper = gson.fromJson(jsonString, QuestionResponseWrapper.class);
		return wrapper.questions;
	}
	
	public static List<Question> getQuestions(String crewMemberName, int startIndex, int endIndex) throws IOException {
		Document doc = Jsoup.connect(endpointDestination + "/question/" + crewMemberName + "/" + startIndex + "/" + endIndex).get();
		String jsonString = doc.text();
		QuestionResponseWrapper wrapper = gson.fromJson(jsonString, QuestionResponseWrapper.class);
		return wrapper.questions;
	}
	
	private class ChatTopicResponseWrapper {
		@SerializedName("chat_topics")
		public List<ChatTopic> chatTopics;	
	}
	
	private class QuestionResponseWrapper {
		public List<Question> questions;
	}
	
	private class EpisodeResponseWrapper {
		public List<Episode> episodes;
	}
}
