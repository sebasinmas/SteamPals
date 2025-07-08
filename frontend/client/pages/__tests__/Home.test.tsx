import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Home from "../Home";

// Mock useNavigate
const mockNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

// Mock Framer Motion
vi.mock("framer-motion", () => ({
  motion: {
    div: ({ children, ...props }: any) => <div {...props}>{children}</div>,
  },
  useMotionValue: () => ({ set: vi.fn() }),
  useTransform: () => 0,
}));

const renderHome = () => {
  return render(
    <BrowserRouter>
      <Home />
    </BrowserRouter>,
  );
};

describe("Home Component", () => {
  beforeEach(() => {
    mockNavigate.mockClear();
  });

  describe("Rendering", () => {
    it("should render main header with SteamPals branding", () => {
      renderHome();
      expect(screen.getByText("SteamPals")).toBeInTheDocument();
    });

    it("should render navigation buttons", () => {
      renderHome();
      expect(screen.getByText("Pals")).toBeInTheDocument();
      expect(screen.getByText("Chat")).toBeInTheDocument();
      expect(screen.getByText("Mi Perfil")).toBeInTheDocument();
      expect(screen.getByText("Config")).toBeInTheDocument();
    });

    it("should render matching interface", () => {
      renderHome();
      expect(
        screen.getByText("Encuentra tu squad perfecto"),
      ).toBeInTheDocument();
      expect(
        screen.getByText("Desliza a la derecha si te gusta, izquierda si no"),
      ).toBeInTheDocument();
    });

    it("should render profile card with user information", () => {
      renderHome();
      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.getByText("Madrid, España")).toBeInTheDocument();
    });

    it("should render action buttons", () => {
      renderHome();
      const rejectButton = screen.getByRole("button", { name: /X/ });
      const acceptButton = screen.getByRole("button", { name: /Gamepad2/ });

      expect(rejectButton).toBeInTheDocument();
      expect(acceptButton).toBeInTheDocument();
    });
  });

  describe("Navigation", () => {
    it("should navigate to chat when chat button is clicked", () => {
      renderHome();
      const chatButton = screen.getByText("Chat").closest("button");
      fireEvent.click(chatButton!);

      expect(mockNavigate).toHaveBeenCalledWith("/chat");
    });

    it("should navigate to profile when profile button is clicked", () => {
      renderHome();
      const profileButton = screen.getByText("Mi Perfil").closest("button");
      fireEvent.click(profileButton!);

      expect(mockNavigate).toHaveBeenCalledWith("/profile");
    });

    it("should navigate to settings when config button is clicked", () => {
      renderHome();
      const settingsButton = screen.getByText("Config").closest("button");
      fireEvent.click(settingsButton!);

      expect(mockNavigate).toHaveBeenCalledWith("/settings");
    });
  });

  describe("Matching System", () => {
    it("should handle swipe right (accept) action", async () => {
      renderHome();
      const acceptButton = screen.getByRole("button", { name: /Gamepad2/ });

      fireEvent.click(acceptButton);

      // Should move to next profile
      await waitFor(() => {
        expect(screen.getByText("StarGazer_22")).toBeInTheDocument();
      });
    });

    it("should handle swipe left (reject) action", async () => {
      renderHome();
      const rejectButton = screen.getByRole("button", { name: /X/ });

      fireEvent.click(rejectButton);

      // Should move to next profile
      await waitFor(() => {
        expect(screen.getByText("StarGazer_22")).toBeInTheDocument();
      });
    });

    it("should show matches view when pals button is clicked", () => {
      renderHome();
      const palsButton = screen.getByText("Pals").closest("button");

      fireEvent.click(palsButton!);

      expect(screen.getByText("Tus Pals 🎮")).toBeInTheDocument();
    });

    it("should show end message when all profiles are swiped", async () => {
      renderHome();
      const acceptButton = screen.getByRole("button", { name: /Gamepad2/ });

      // Swipe through all profiles
      for (let i = 0; i < 4; i++) {
        fireEvent.click(acceptButton);
        await waitFor(() => {}, { timeout: 100 });
      }

      expect(screen.getByText("¡No hay más perfiles!")).toBeInTheDocument();
    });
  });

  describe("Chat Creation", () => {
    it("should create chat when chat button is clicked in matches", async () => {
      renderHome();

      // Accept a profile to create a match
      const acceptButton = screen.getByRole("button", { name: /Gamepad2/ });
      fireEvent.click(acceptButton);

      // Go to matches view
      const palsButton = screen.getByText("Pals").closest("button");
      fireEvent.click(palsButton!);

      // Click chat button in match
      const chatButton = screen.getByText("Chat");
      fireEvent.click(chatButton);

      // Should navigate to chat
      expect(mockNavigate).toHaveBeenCalledWith("/chat");
    });
  });

  describe("Features Display", () => {
    it("should render feature cards", () => {
      renderHome();
      expect(screen.getByText("Comunidad")).toBeInTheDocument();
      expect(screen.getByText("Torneos")).toBeInTheDocument();
      expect(screen.getByText("Juegos")).toBeInTheDocument();
    });

    it("should display correct feature descriptions", () => {
      renderHome();
      expect(screen.getByText("Miles de gamers")).toBeInTheDocument();
      expect(screen.getByText("Competiciones épicas")).toBeInTheDocument();
      expect(screen.getByText("Todos los géneros")).toBeInTheDocument();
    });
  });

  describe("Profile Information", () => {
    it("should display user stats correctly", () => {
      renderHome();
      expect(screen.getByText("Lvl 87")).toBeInTheDocument();
      expect(screen.getByText("2,450h")).toBeInTheDocument();
      expect(screen.getByText("342 logros")).toBeInTheDocument();
    });

    it("should show online status", () => {
      renderHome();
      expect(screen.getByText("En línea")).toBeInTheDocument();
    });

    it("should display favorite games", () => {
      renderHome();
      expect(screen.getByText("CS2")).toBeInTheDocument();
      expect(screen.getByText("Valorant")).toBeInTheDocument();
      expect(screen.getByText("Elden Ring")).toBeInTheDocument();
    });
  });

  describe("Error Handling", () => {
    it("should handle empty matches gracefully", () => {
      renderHome();
      const palsButton = screen.getByText("Pals").closest("button");
      fireEvent.click(palsButton!);

      expect(screen.getByText("Aún no tienes pals.")).toBeInTheDocument();
    });

    it("should handle navigation errors gracefully", () => {
      mockNavigate.mockImplementation(() => {
        throw new Error("Navigation failed");
      });

      renderHome();
      const chatButton = screen.getByText("Chat").closest("button");

      expect(() => fireEvent.click(chatButton!)).not.toThrow();
    });
  });

  describe("Accessibility", () => {
    it("should have proper ARIA labels", () => {
      renderHome();
      const buttons = screen.getAllByRole("button");

      buttons.forEach((button) => {
        expect(button).toBeInTheDocument();
      });
    });

    it("should support keyboard navigation", () => {
      renderHome();
      const acceptButton = screen.getByRole("button", { name: /Gamepad2/ });

      acceptButton.focus();
      expect(document.activeElement).toBe(acceptButton);
    });
  });
});
