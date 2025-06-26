import React, { useEffect, useRef } from "react";
import Hammer from "hammerjs";
import "./custom.css";  // aquí va el CSS que no se puede hacer en Tailwind

const profiles = [
  {
    id: 1,
    name: "Diego Brando",
    age: 21,
    description: "Za warudo",
    image: "https://assets.nintendo.com/image/upload/ar_16:9,b_auto:border,c_lpad/b_white/f_auto/q_auto/dpr_1.5/c_scale,w_500/ncom/software/switch/70050000034894/a76d0cab0bb7357322c970e79638adf444b42cd56d0d8ebf3ad2115a9e9662cd"
  },
  // agrega más perfiles...
];

export default function Tinder() {
  const tinderRef = useRef(null);
  const cardsRef = useRef([]);

  useEffect(() => {
    const tinderContainer = tinderRef.current;
    const allCards = cardsRef.current;

    function initCards() {
      const newCards = allCards.filter(card => card && !card.classList.contains("removed"));

      newCards.forEach((card, index) => {
        card.style.zIndex = newCards.length - index;
        card.style.transform = `scale(${(20 - index) / 20}) translateY(-${30 * index}px)`;
        card.style.opacity = (10 - index) / 10;
      });

      tinderContainer.classList.add("loaded");
    }

    initCards();

    allCards.forEach(card => {
      if (!card) return;
      const hammer = new Hammer(card);

      hammer.on("pan", event => {
        card.classList.add("moving");

        if (event.deltaX === 0) return;
        if (event.center.x === 0 && event.center.y === 0) return;

        tinderContainer.classList.toggle("tinder_love", event.deltaX > 0);
        tinderContainer.classList.toggle("tinder_nope", event.deltaX < 0);

        const xMulti = event.deltaX * 0.03;
        const yMulti = event.deltaY / 80;
        const rotate = xMulti * yMulti;

        card.style.transform = `translate(${event.deltaX}px, ${event.deltaY}px) rotate(${rotate}deg)`;
      });

      hammer.on("panend", event => {
        card.classList.remove("moving");
        tinderContainer.classList.remove("tinder_love");
        tinderContainer.classList.remove("tinder_nope");

        const moveOutWidth = document.body.clientWidth;
        const keep = Math.abs(event.deltaX) < 80 || Math.abs(event.velocityX) < 0.5;

        card.classList.toggle("removed", !keep);

        if (keep) {
          card.style.transform = "";
        } else {
          const endX = Math.max(Math.abs(event.velocityX) * moveOutWidth, moveOutWidth);
          const toX = event.deltaX > 0 ? endX : -endX;
          const endY = Math.abs(event.velocityY) * moveOutWidth;
          const toY = event.deltaY > 0 ? endY : -endY;
          const xMulti = event.deltaX * 0.03;
          const yMulti = event.deltaY / 80;
          const rotate = xMulti * yMulti;

          card.style.transform = `translate(${toX}px, ${toY + event.deltaY}px) rotate(${rotate}deg)`;

          initCards();
        }
      });
    });

    function createButtonListener(love) {
      return function (event) {
        const cards = cardsRef.current.filter(card => card && !card.classList.contains("removed"));
        if (!cards.length) return;

        const card = cards[0];
        const moveOutWidth = document.body.clientWidth * 1.5;

        card.classList.add("removed");

        if (love) {
          card.style.transform = `translate(${moveOutWidth}px, -100px) rotate(-30deg)`;
        } else {
          card.style.transform = `translate(-${moveOutWidth}px, -100px) rotate(30deg)`;
        }

        initCards();

        event.preventDefault();
      };
    }

    const nopeBtn = document.getElementById("nope");
    const loveBtn = document.getElementById("love");

    nopeBtn?.addEventListener("click", createButtonListener(false));
    loveBtn?.addEventListener("click", createButtonListener(true));

    // Limpieza al desmontar
    return () => {
      nopeBtn?.removeEventListener("click", createButtonListener(false));
      loveBtn?.removeEventListener("click", createButtonListener(true));
      allCards.forEach(card => {
        if(card?.hammer) card.hammer.destroy();
      });
    };
  }, []);

  return (
    <div ref={tinderRef} className="tinder relative flex flex-col w-screen h-screen overflow-hidden">
      <div className="tinder--status pointer-events-none absolute top-1/2 w-full text-center z-20 -mt-8">
        <i className="fa fa-remove absolute left-1/2 text-[100px] opacity-0 scale-75 -ml-12 transition-all duration-200 ease-in-out"></i>
        <i className="fa fa-heart absolute left-1/2 text-[100px] opacity-0 scale-75 -ml-12 transition-all duration-200 ease-in-out"></i>
      </div>

      <div className="tinder--cards flex flex-grow pt-10 justify-center items-end relative z-10">
        {profiles.map((profile, i) => (
          <div
            key={profile.id}
            ref={el => cardsRef.current[i] = el}
             className="bg-white rounded-2xl shadow-xl
             w-[98vw] max-w-[900px] h-[90vh] p-8
             flex flex-col"          >
            <img
              src={profile.image}
              alt={profile.name}
              className="max-w-full pointer-events-none"
            />
            <h3 className="mt-8 text-2xl px-4 pointer-events-none">{profile.name}, {profile.age}</h3>
            <p className="mt-6 text-lg px-4 pointer-events-none">{profile.description}</p>
          </div>
        ))}
      </div>

      <div className="tinder--buttons flex justify-center pt-5 flex-none space-x-4">
        <button
          id="nope"
          className="rounded-full w-16 h-16 bg-white border-0 inline-block m-2 shadow hover:bg-gray-200 focus:outline-none"
        >
          <i className="fa fa-remove text-2xl"></i>
        </button>
        <button
          id="love"
          className="rounded-full w-16 h-16 bg-white border-0 inline-block m-2 shadow hover:bg-gray-200 focus:outline-none"
        >
          <i className="fa fa-heart text-2xl text-pink-400"></i>
        </button>
      </div>
    </div>
  );
}
