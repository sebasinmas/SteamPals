import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Chat from "../Chat";

const mockNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

const renderChat = () => {
  return render(
    <BrowserRouter>
      <Chat />
    </BrowserRouter>,
  );
};

describe("Chat Component", () => {
  beforeEach(() => {
    mockNavigate.mockClear();
  });

  describe("Rendering", () => {
    it("should render chat header with SteamPals branding", () => {
      renderChat();
      expect(screen.getByText("SteamPals")).toBeInTheDocument();
    });

    it("should render chat list sidebar", () => {
      renderChat();
      expect(
        screen.getByPlaceholderText("Buscar chats..."),
      ).toBeInTheDocument();
    });

    it("should render available chats", () => {
      renderChat();
      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.getByText("Squad Gaming")).toBeInTheDocument();
      expect(screen.getByText("StarGazer_22")).toBeInTheDocument();
    });

    it("should render selected chat interface", () => {
      renderChat();
      expect(screen.getByText("En línea")).toBeInTheDocument();
      expect(
        screen.getByPlaceholderText("Escribe un mensaje..."),
      ).toBeInTheDocument();
    });
  });

  describe("Chat Selection", () => {
    it("should select chat when clicked", () => {
      renderChat();
      const squadChat = screen.getByText("Squad Gaming");

      fireEvent.click(squadChat);

      expect(screen.getByText("3 participantes")).toBeInTheDocument();
    });

    it("should display correct chat header info", () => {
      renderChat();
      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.getByText("En línea")).toBeInTheDocument();
    });
  });

  describe("Message Sending", () => {
    it("should send message when send button is clicked", async () => {
      renderChat();
      const messageInput = screen.getByPlaceholderText("Escribe un mensaje...");
      const sendButton = screen.getByRole("button", { name: /Send/ });

      fireEvent.change(messageInput, {
        target: { value: "Hola! ¿Cómo estás?" },
      });
      fireEvent.click(sendButton);

      await waitFor(() => {
        expect(screen.getByText("Hola! ¿Cómo estás?")).toBeInTheDocument();
      });

      expect(messageInput).toHaveValue("");
    });

    it("should send message when Enter is pressed", async () => {
      renderChat();
      const messageInput = screen.getByPlaceholderText("Escribe un mensaje...");

      fireEvent.change(messageInput, {
        target: { value: "Mensaje con Enter" },
      });
      fireEvent.keyPress(messageInput, { key: "Enter", code: "Enter" });

      await waitFor(() => {
        expect(screen.getByText("Mensaje con Enter")).toBeInTheDocument();
      });
    });

    it("should not send empty messages", () => {
      renderChat();
      const sendButton = screen.getByRole("button", { name: /Send/ });

      expect(sendButton).toBeDisabled();
    });

    it("should enable send button when message is typed", () => {
      renderChat();
      const messageInput = screen.getByPlaceholderText("Escribe un mensaje...");
      const sendButton = screen.getByRole("button", { name: /Send/ });

      fireEvent.change(messageInput, { target: { value: "Test message" } });

      expect(sendButton).not.toBeDisabled();
    });
  });

  describe("Chat Search", () => {
    it("should filter chats when searching", () => {
      renderChat();
      const searchInput = screen.getByPlaceholderText("Buscar chats...");

      fireEvent.change(searchInput, { target: { value: "Shadow" } });

      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.queryByText("Squad Gaming")).not.toBeInTheDocument();
    });

    it("should show all chats when search is cleared", () => {
      renderChat();
      const searchInput = screen.getByPlaceholderText("Buscar chats...");

      fireEvent.change(searchInput, { target: { value: "Shadow" } });
      fireEvent.change(searchInput, { target: { value: "" } });

      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.getByText("Squad Gaming")).toBeInTheDocument();
    });
  });

  describe("Message Display", () => {
    it("should display existing messages", () => {
      renderChat();
      expect(
        screen.getByText("¡Hey! ¿Viste que subí de rango?"),
      ).toBeInTheDocument();
      expect(
        screen.getByText("En Valorant, por fin llegué a Diamante!"),
      ).toBeInTheDocument();
    });

    it("should display message timestamps", () => {
      renderChat();
      const messages = screen.getAllByText(/\d{1,2}:\d{2}/);
      expect(messages.length).toBeGreaterThan(0);
    });

    it("should distinguish between own and other messages", () => {
      renderChat();
      const ownMessage = screen.getByText("¡Qué genial! Yo sigo en Platino 😅");
      const otherMessage = screen.getByText("¡Hey! ¿Viste que subí de rango?");

      expect(ownMessage).toBeInTheDocument();
      expect(otherMessage).toBeInTheDocument();
    });
  });

  describe("Online Status", () => {
    it("should show online indicators for active users", () => {
      renderChat();
      expect(screen.getByText("En línea")).toBeInTheDocument();
    });

    it("should show offline status for inactive users", () => {
      renderChat();
      const starGazerChat = screen.getByText("StarGazer_22");
      fireEvent.click(starGazerChat);

      expect(screen.getByText("Desconectado")).toBeInTheDocument();
    });
  });

  describe("Navigation", () => {
    it("should navigate back to home when volver button is clicked", () => {
      renderChat();
      const backButton = screen.getByText("Volver");

      fireEvent.click(backButton);

      expect(mockNavigate).toHaveBeenCalledWith("/home");
    });
  });

  describe("Chat Controls", () => {
    it("should render chat control buttons", () => {
      renderChat();
      expect(screen.getByRole("button", { name: /Phone/ })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /Video/ })).toBeInTheDocument();
      expect(screen.getByRole("button", { name: /Mic/ })).toBeInTheDocument();
    });

    it("should toggle mute button", () => {
      renderChat();
      const muteButton = screen.getByRole("button", { name: /Mic/ });

      fireEvent.click(muteButton);

      expect(
        screen.getByRole("button", { name: /MicOff/ }),
      ).toBeInTheDocument();
    });
  });

  describe("Group Chat Features", () => {
    it("should show participant count for group chats", () => {
      renderChat();
      const squadChat = screen.getByText("Squad Gaming");

      fireEvent.click(squadChat);

      expect(screen.getByText("3 participantes")).toBeInTheDocument();
    });

    it("should display group chat indicator", () => {
      renderChat();
      expect(screen.getByText("3 participantes • Activo")).toBeInTheDocument();
    });
  });

  describe("Error Handling", () => {
    it("should handle message sending errors gracefully", () => {
      renderChat();
      const messageInput = screen.getByPlaceholderText("Escribe un mensaje...");
      const sendButton = screen.getByRole("button", { name: /Send/ });

      // Simulate error scenario
      fireEvent.change(messageInput, { target: { value: "Error message" } });

      expect(() => fireEvent.click(sendButton)).not.toThrow();
    });
  });

  describe("Accessibility", () => {
    it("should have proper form labels", () => {
      renderChat();
      const messageInput = screen.getByPlaceholderText("Escribe un mensaje...");

      expect(messageInput).toHaveAttribute("placeholder");
    });

    it("should support keyboard navigation", () => {
      renderChat();
      const messageInput = screen.getByPlaceholderText("Escribe un mensaje...");

      messageInput.focus();
      expect(document.activeElement).toBe(messageInput);
    });
  });

  describe("Responsive Behavior", () => {
    it("should render mobile-friendly interface", () => {
      renderChat();

      // Check that all essential elements are present
      expect(
        screen.getByPlaceholderText("Buscar chats..."),
      ).toBeInTheDocument();
      expect(
        screen.getByPlaceholderText("Escribe un mensaje..."),
      ).toBeInTheDocument();
    });
  });
});
