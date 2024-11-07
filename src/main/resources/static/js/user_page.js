document.addEventListener("DOMContentLoaded", function () {
    // Get all the sidebar links
    const links = document.querySelectorAll('.slidebar a');

    // Function to hide all sections
    function hideSections() {
        const sections = document.querySelectorAll('.mainContent > div');
        sections.forEach(function(section) {
            section.style.display = 'none';
        });
    }

    // Function to show the section based on the hash
    function showSectionFromHash() {
        const sectionId = window.location.hash.substring(1); // Get the section ID from the URL hash
        hideSections();
        if (sectionId) {
            const section = document.getElementById(sectionId);
            if (section) {
                section.style.display = 'block';
            }
        }
    }

    // Function to manage the active state of the links
    function updateActiveLink() {
        links.forEach(function(link) {
            if (link.getAttribute('href') === window.location.hash) {
                link.classList.add('active');
            } else {
                link.classList.remove('active');
            }
        });
    }

    // Listen for hash changes
    window.addEventListener('hashchange', function() {
        showSectionFromHash();
        updateActiveLink();
    });

    // Click event on sidebar links
    links.forEach(function(link) {
        link.addEventListener('click', function(event) {
            // We do not need to call event.preventDefault() here anymore
            window.location.hash = link.getAttribute('href').split('#')[1]; // Update the URL hash
        });
    });

    // Show the section based on the current hash on page load
    if (window.location.hash) {
        showSectionFromHash();
        updateActiveLink();
    } else {
        // Optionally show the first section by default (e.g., Dashboard)
        window.location.hash = "#dashboard";
    }
});
