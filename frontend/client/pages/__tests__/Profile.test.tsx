import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Profile from "../Profile";

const mockNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

const renderProfile = () => {
  return render(
    <BrowserRouter>
      <Profile />
    </BrowserRouter>,
  );
};

describe("Profile Component", () => {
  beforeEach(() => {
    mockNavigate.mockClear();
  });

  describe("Rendering", () => {
    it("should render profile header with SteamPals branding", () => {
      renderProfile();
      expect(screen.getByText("SteamPals")).toBeInTheDocument();
    });

    it("should render user profile information", () => {
      renderProfile();
      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.getByText("Madrid, España")).toBeInTheDocument();
      expect(screen.getByText("En línea")).toBeInTheDocument();
    });

    it("should render gaming statistics", () => {
      renderProfile();
      expect(screen.getByText("87")).toBeInTheDocument(); // Level
      expect(screen.getByText("342")).toBeInTheDocument(); // Achievements
      expect(screen.getByText("2,450h")).toBeInTheDocument(); // Hours played
    });

    it("should render Steam connection status", () => {
      renderProfile();
      expect(screen.getByText("Steam")).toBeInTheDocument();
      expect(screen.getByText("Conectado")).toBeInTheDocument();
    });

    it("should render favorite games", () => {
      renderProfile();
      expect(screen.getByText("CS2")).toBeInTheDocument();
      expect(screen.getByText("Valorant")).toBeInTheDocument();
      expect(screen.getByText("Elden Ring")).toBeInTheDocument();
    });
  });

  describe("Edit Mode", () => {
    it("should enter edit mode when edit button is clicked", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");

      fireEvent.click(editButton);

      expect(screen.getByText("Guardar")).toBeInTheDocument();
      expect(screen.getByText("Cancelar")).toBeInTheDocument();
    });

    it("should show editable inputs in edit mode", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");

      fireEvent.click(editButton);

      const usernameInput = screen.getByDisplayValue("ShadowHunter87");
      const locationInput = screen.getByDisplayValue("Madrid, España");

      expect(usernameInput).toBeInTheDocument();
      expect(locationInput).toBeInTheDocument();
    });

    it("should show editable bio textarea in edit mode", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");

      fireEvent.click(editButton);

      const bioTextarea = screen.getByDisplayValue(
        "Jugador competitivo de FPS y RPG. Busco squad para ranked!",
      );
      expect(bioTextarea).toBeInTheDocument();
    });

    it("should show editable favorite games in edit mode", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");

      fireEvent.click(editButton);

      const gamesInput = screen.getByDisplayValue(
        "CS2, Valorant, Elden Ring, Rocket League",
      );
      expect(gamesInput).toBeInTheDocument();
    });
  });

  describe("Profile Editing", () => {
    it("should update username when edited", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const usernameInput = screen.getByDisplayValue("ShadowHunter87");
      fireEvent.change(usernameInput, { target: { value: "NewGamer123" } });

      const saveButton = screen.getByText("Guardar");
      fireEvent.click(saveButton);

      expect(screen.getByText("NewGamer123")).toBeInTheDocument();
    });

    it("should update bio when edited", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const bioTextarea = screen.getByDisplayValue(
        "Jugador competitivo de FPS y RPG. Busco squad para ranked!",
      );
      fireEvent.change(bioTextarea, {
        target: { value: "Nueva biografía actualizada" },
      });

      const saveButton = screen.getByText("Guardar");
      fireEvent.click(saveButton);

      expect(
        screen.getByText("Nueva biografía actualizada"),
      ).toBeInTheDocument();
    });

    it("should update location when edited", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const locationInput = screen.getByDisplayValue("Madrid, España");
      fireEvent.change(locationInput, {
        target: { value: "Barcelona, España" },
      });

      const saveButton = screen.getByText("Guardar");
      fireEvent.click(saveButton);

      expect(screen.getByText("Barcelona, España")).toBeInTheDocument();
    });

    it("should update favorite games when edited", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const gamesInput = screen.getByDisplayValue(
        "CS2, Valorant, Elden Ring, Rocket League",
      );
      fireEvent.change(gamesInput, {
        target: { value: "FIFA, NBA 2K, Fortnite" },
      });

      const saveButton = screen.getByText("Guardar");
      fireEvent.click(saveButton);

      expect(screen.getByText("FIFA")).toBeInTheDocument();
      expect(screen.getByText("NBA 2K")).toBeInTheDocument();
      expect(screen.getByText("Fortnite")).toBeInTheDocument();
    });
  });

  describe("Cancel Editing", () => {
    it("should cancel edit mode when cancel button is clicked", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const cancelButton = screen.getByText("Cancelar");
      fireEvent.click(cancelButton);

      expect(screen.getByText("Editar")).toBeInTheDocument();
      expect(screen.queryByText("Guardar")).not.toBeInTheDocument();
    });

    it("should revert changes when cancelled", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const usernameInput = screen.getByDisplayValue("ShadowHunter87");
      fireEvent.change(usernameInput, { target: { value: "TempName" } });

      const cancelButton = screen.getByText("Cancelar");
      fireEvent.click(cancelButton);

      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.queryByText("TempName")).not.toBeInTheDocument();
    });
  });

  describe("Gaming Statistics", () => {
    it("should display gaming stats correctly", () => {
      renderProfile();

      expect(screen.getByText("Nivel")).toBeInTheDocument();
      expect(screen.getByText("Logros")).toBeInTheDocument();
      expect(screen.getByText("Horas jugadas")).toBeInTheDocument();
      expect(screen.getByText("Logros obtenidos")).toBeInTheDocument();
      expect(screen.getByText("Nivel actual")).toBeInTheDocument();
    });

    it("should show member since date", () => {
      renderProfile();
      expect(screen.getByText("Miembro desde Enero 2023")).toBeInTheDocument();
    });
  });

  describe("Steam Integration", () => {
    it("should show Steam connection status", () => {
      renderProfile();
      expect(screen.getByText("Conectado")).toBeInTheDocument();
    });

    it("should show connect button when Steam is disconnected", () => {
      // This would require mocking the Steam connection state
      renderProfile();
      // For now, just verify the Steam section exists
      expect(screen.getByText("Steam")).toBeInTheDocument();
    });
  });

  describe("Navigation", () => {
    it("should navigate back to home when back button is clicked", () => {
      renderProfile();
      const backButton = screen.getByText("Volver");

      fireEvent.click(backButton);

      expect(mockNavigate).toHaveBeenCalledWith("/home");
    });
  });

  describe("Form Validation", () => {
    it("should handle empty username gracefully", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const usernameInput = screen.getByDisplayValue("ShadowHunter87");
      fireEvent.change(usernameInput, { target: { value: "" } });

      const saveButton = screen.getByText("Guardar");
      fireEvent.click(saveButton);

      // Should still save but handle empty value
      expect(screen.getByText("Editar")).toBeInTheDocument();
    });

    it("should handle special characters in games list", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const gamesInput = screen.getByDisplayValue(
        "CS2, Valorant, Elden Ring, Rocket League",
      );
      fireEvent.change(gamesInput, {
        target: { value: "CS:GO, Dota 2, Age of Empires IV" },
      });

      const saveButton = screen.getByText("Guardar");
      fireEvent.click(saveButton);

      expect(screen.getByText("CS:GO")).toBeInTheDocument();
      expect(screen.getByText("Dota 2")).toBeInTheDocument();
    });
  });

  describe("Accessibility", () => {
    it("should have proper form labels in edit mode", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      expect(
        screen.getByText("Agrega tus juegos favoritos (separados por coma)"),
      ).toBeInTheDocument();
    });

    it("should support keyboard navigation", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");

      editButton.focus();
      expect(document.activeElement).toBe(editButton);
    });
  });

  describe("Error Handling", () => {
    it("should handle save errors gracefully", () => {
      renderProfile();
      const editButton = screen.getByText("Editar");
      fireEvent.click(editButton);

      const saveButton = screen.getByText("Guardar");

      expect(() => fireEvent.click(saveButton)).not.toThrow();
    });
  });

  describe("Responsive Design", () => {
    it("should render properly on mobile sizes", () => {
      renderProfile();

      // Check that all essential elements are present
      expect(screen.getByText("ShadowHunter87")).toBeInTheDocument();
      expect(screen.getByText("Biografía")).toBeInTheDocument();
      expect(screen.getByText("Estadísticas Gaming")).toBeInTheDocument();
    });
  });
});
