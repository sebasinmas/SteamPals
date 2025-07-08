import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent } from "@/components/ui/card";
import {
  Gamepad2,
  ArrowLeft,
  Send,
  Paperclip,
  Smile,
  MoreVertical,
  Phone,
  Video,
  Search,
  Users,
  Settings,
  Image,
  Mic,
  MicOff,
} from "lucide-react";

interface Message {
  id: string;
  senderId: string;
  senderName: string;
  senderAvatar: string;
  content: string;
  timestamp: Date;
  isMe: boolean;
}

interface ChatRoom {
  id: string;
  name: string;
  avatar: string;
  isGroup: boolean;
  participants: string[];
  lastMessage: string;
  lastMessageTime: string;
  unreadCount: number;
  isOnline: boolean;
}

const initialMessages: { [chatId: string]: Message[] } = {};

export default function Chat() {
  const navigate = useNavigate();
  const [selectedChat, setSelectedChat] = useState<string | null>(null);
  const [message, setMessage] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [isMuted, setIsMuted] = useState(false);
  const [allMessages, setAllMessages] = useState<{
    [chatId: string]: Message[];
  }>(initialMessages);
  const [dynamicChatRooms, setDynamicChatRooms] = useState<ChatRoom[]>([]);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  // Cargar chats persistentes
  useEffect(() => {
    const savedChats = localStorage.getItem("steamPalsChats");

    if (savedChats) {
      const parsedChats = JSON.parse(savedChats);
      const convertedChats: ChatRoom[] = parsedChats.map((chat: any) => ({
        id: chat.id,
        name: chat.name,
        avatar: chat.avatar,
        isGroup: chat.isGroup,
        participants: chat.participants || [chat.name],
        lastMessage: chat.lastMessage,
        lastMessageTime: chat.timestamp,
        unreadCount: 0,
        isOnline: true,
      }));

      setDynamicChatRooms(convertedChats);

      if (convertedChats.length > 0) {
        alert(`💬 Cargados ${convertedChats.length} chats en componente Chat`);
      }
    }
  }, []);

  const staticChatRooms: ChatRoom[] = [];

  const chatRooms = [...staticChatRooms, ...dynamicChatRooms];

  const currentChat = chatRooms.find((chat) => chat.id === selectedChat);
  const currentMessages = selectedChat ? allMessages[selectedChat] || [] : [];

  const sendMessage = () => {
    if (!message.trim() || !selectedChat) return;

    const newMessage: Message = {
      id: Date.now().toString(),
      senderId: "me",
      senderName: "Tú",
      senderAvatar: "👤",
      content: message.trim(),
      timestamp: new Date(),
      isMe: true,
    };

    // Add message to current chat
    setAllMessages((prev) => ({
      ...prev,
      [selectedChat]: [...(prev[selectedChat] || []), newMessage],
    }));

    console.log("Mensaje enviado:", message);
    setMessage("");
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [currentMessages]);

  const filteredChats = chatRooms.filter((chat) =>
    chat.name.toLowerCase().includes(searchTerm.toLowerCase()),
  );

  return (
    <div className="min-h-screen bg-gradient-to-br from-background via-background to-card">
      {/* Header */}
      <header className="border-b border-border/50 backdrop-blur-sm bg-background/80 sticky top-0 z-[60]">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <Link to="/home" className="flex items-center space-x-3">
              <div className="relative">
                <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                  <Gamepad2 className="w-6 h-6 text-primary-foreground" />
                </div>
                <div className="absolute -top-1 -right-1 w-4 h-4 bg-accent rounded-full animate-pulse"></div>
              </div>
              <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                SteamPals
              </h1>
            </Link>
            <Link to="/home">
              <Button variant="ghost" className="hover:bg-muted/50">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Volver
              </Button>
            </Link>
          </div>
        </div>
      </header>

      <div className="flex h-[calc(100vh-80px)]">
        {/* Chat List Sidebar */}
        <div className="w-80 border-r border-border/50 bg-card/50 backdrop-blur-sm">
          <div className="p-4 border-b border-border/50">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
              <Input
                placeholder="Buscar chats..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
          </div>

          <div className="overflow-y-auto h-full">
            {filteredChats.map((chat) => (
              <div
                key={chat.id}
                onClick={() => setSelectedChat(chat.id)}
                className={`p-4 border-b border-border/30 cursor-pointer transition-colors hover:bg-muted/30 ${
                  selectedChat === chat.id ? "bg-muted/50" : ""
                }`}
              >
                <div className="flex items-center space-x-3">
                  <div className="relative">
                    {chat.isGroup ? (
                      <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                        <Users className="w-6 h-6 text-primary-foreground" />
                      </div>
                    ) : (
                      <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-xl">
                        {chat.avatar}
                      </div>
                    )}
                    {!chat.isGroup && (
                      <div
                        className={`absolute bottom-0 right-0 w-4 h-4 rounded-full border-2 border-background ${
                          chat.isOnline ? "bg-green-400" : "bg-gray-400"
                        }`}
                      ></div>
                    )}
                  </div>

                  <div className="flex-1 min-w-0">
                    <div className="flex items-center justify-between">
                      <h3 className="font-medium truncate">{chat.name}</h3>
                      <span className="text-xs text-muted-foreground">
                        {chat.lastMessageTime}
                      </span>
                    </div>
                    <p className="text-sm text-muted-foreground truncate mt-1">
                      {chat.lastMessage}
                    </p>
                    {chat.isGroup && (
                      <p className="text-xs text-muted-foreground mt-1">
                        {chat.participants.length} participantes
                      </p>
                    )}
                  </div>

                  {chat.unreadCount > 0 && (
                    <Badge className="bg-accent text-xs min-w-[20px] h-5 flex items-center justify-center">
                      {chat.unreadCount}
                    </Badge>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Chat Area */}
        <div className="flex-1 flex flex-col">
          {selectedChat && currentChat ? (
            <>
              {/* Chat Header */}
              <div className="p-4 border-b border-border/50 bg-card/80 backdrop-blur-sm sticky top-[80px] z-[55]">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-3">
                    <div className="relative">
                      {currentChat.isGroup ? (
                        <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                          <Users className="w-5 h-5 text-primary-foreground" />
                        </div>
                      ) : (
                        <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-lg">
                          {currentChat.avatar}
                        </div>
                      )}
                      {!currentChat.isGroup && (
                        <div
                          className={`absolute bottom-0 right-0 w-3 h-3 rounded-full border-2 border-background ${
                            currentChat.isOnline
                              ? "bg-green-400"
                              : "bg-gray-400"
                          }`}
                        ></div>
                      )}
                    </div>
                    <div>
                      <button
                        onClick={() => {
                          if (!currentChat.isGroup) {
                            navigate(`/user/${currentChat.name}`);
                          }
                        }}
                        className={`font-semibold text-left ${
                          !currentChat.isGroup
                            ? "text-primary hover:text-accent transition-colors cursor-pointer underline decoration-transparent hover:decoration-current"
                            : "cursor-default"
                        }`}
                        disabled={currentChat.isGroup}
                      >
                        {currentChat.name}
                      </button>
                      <p className="text-sm text-muted-foreground">
                        {currentChat.isGroup
                          ? `${currentChat.participants.length} participantes`
                          : currentChat.isOnline
                            ? "En línea"
                            : "Desconectado"}
                      </p>
                    </div>
                  </div>

                  <div className="flex items-center space-x-2">
                    <Button variant="ghost" size="sm">
                      <Phone className="w-4 h-4" />
                    </Button>
                    <Button variant="ghost" size="sm">
                      <Video className="w-4 h-4" />
                    </Button>
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={() => setIsMuted(!isMuted)}
                    >
                      {isMuted ? (
                        <MicOff className="w-4 h-4" />
                      ) : (
                        <Mic className="w-4 h-4" />
                      )}
                    </Button>
                    <Button variant="ghost" size="sm">
                      <MoreVertical className="w-4 h-4" />
                    </Button>
                  </div>
                </div>
              </div>

              {/* Messages */}
              <div className="flex-1 overflow-y-auto p-4 space-y-4">
                {currentMessages.map((msg) => (
                  <div
                    key={msg.id}
                    className={`flex ${msg.isMe ? "justify-end" : "justify-start"}`}
                  >
                    <div
                      className={`flex items-end space-x-2 max-w-xs lg:max-w-md ${
                        msg.isMe ? "flex-row-reverse space-x-reverse" : ""
                      }`}
                    >
                      {!msg.isMe && (
                        <div className="w-8 h-8 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center text-sm">
                          {msg.senderAvatar}
                        </div>
                      )}
                      <div
                        className={`rounded-lg px-4 py-2 ${
                          msg.isMe
                            ? "bg-primary text-primary-foreground"
                            : "bg-muted"
                        }`}
                      >
                        {!msg.isMe && currentChat.isGroup && (
                          <button
                            onClick={() => navigate(`/user/${msg.senderName}`)}
                            className="text-xs font-medium mb-1 opacity-70 hover:opacity-100 text-primary hover:text-accent transition-colors cursor-pointer underline decoration-transparent hover:decoration-current"
                          >
                            {msg.senderName}
                          </button>
                        )}
                        <p className="text-sm">{msg.content}</p>
                        <p
                          className={`text-xs mt-1 ${
                            msg.isMe
                              ? "text-primary-foreground/70"
                              : "text-muted-foreground"
                          }`}
                        >
                          {msg.timestamp.toLocaleTimeString([], {
                            hour: "2-digit",
                            minute: "2-digit",
                          })}
                        </p>
                      </div>
                    </div>
                  </div>
                ))}
                <div ref={messagesEndRef} />
              </div>

              {/* Message Input */}
              <div className="p-4 border-t border-border/50 bg-card/80 backdrop-blur-sm">
                <div className="flex items-center space-x-2">
                  <div className="flex-1 relative">
                    <Input
                      value={message}
                      onChange={(e) => setMessage(e.target.value)}
                      placeholder="Escribe un mensaje..."
                      onKeyPress={(e) => e.key === "Enter" && sendMessage()}
                      className="pr-10"
                    />
                    <Button
                      variant="ghost"
                      size="sm"
                      className="absolute right-1 top-1/2 transform -translate-y-1/2"
                    >
                      <Smile className="w-4 h-4" />
                    </Button>
                  </div>
                  <Button
                    onClick={sendMessage}
                    disabled={!message.trim()}
                    className="bg-primary hover:bg-primary/90"
                  >
                    <Send className="w-4 h-4" />
                  </Button>
                </div>
              </div>
            </>
          ) : (
            /* No Chat Selected */
            <div className="flex-1 flex items-center justify-center">
              <div className="text-center">
                <div className="w-24 h-24 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center mx-auto mb-4">
                  <Gamepad2 className="w-12 h-12 text-primary-foreground" />
                </div>
                <h2 className="text-2xl font-bold mb-2">
                  ¡Bienvenido a SteamPals Chat!
                </h2>
                <p className="text-muted-foreground">
                  Selecciona un chat para empezar a conversar con tus pals
                </p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
